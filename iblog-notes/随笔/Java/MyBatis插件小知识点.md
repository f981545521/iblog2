背景
mybatis作为一款十分优秀的orm框架在大量的互联网应用中得到使用。其提供了比较完善的插件扩展机制

通常我们会使用插件做到许多共通的事情 比如

慢sql记录
sql性能记录
db主从
分页
乐观锁等
那么简单剖析一下mybatis的插件机制

分析
从本质上来说这也仍然是一个动态代理的过程。一般来说我们可以采用java proxy或者aspectj等来实现

mybatis的插件同样是利用了java的proxy来实现

一个典型的mybatis插件会实现Interceptor类 但是整个故事是从Plugin开始的

来简单看一下Plugin

public class Plugin implements InvocationHandler {

  private final Object target;
  private final Interceptor interceptor;
  private final Map<Class<?>, Set<Method>> signatureMap;

  private Plugin(Object target, Interceptor interceptor, Map<Class<?>, Set<Method>> signatureMap) {
    this.target = target;
    this.interceptor = interceptor;
    this.signatureMap = signatureMap;
  }

  public static Object wrap(Object target, Interceptor interceptor) {
    Map<Class<?>, Set<Method>> signatureMap = getSignatureMap(interceptor);
    Class<?> type = target.getClass();
    Class<?>[] interfaces = getAllInterfaces(type, signatureMap);
    if (interfaces.length > 0) {
      return Proxy.newProxyInstance(
          type.getClassLoader(),
          interfaces,
          new Plugin(target, interceptor, signatureMap));
    }
    return target;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    try {
      Set<Method> methods = signatureMap.get(method.getDeclaringClass());
      if (methods != null && methods.contains(method)) {
        return interceptor.intercept(new Invocation(target, method, args));
      }
      return method.invoke(target, args);
    } catch (Exception e) {
      throw ExceptionUtil.unwrapThrowable(e);
    }
  }

  private static Map<Class<?>, Set<Method>> getSignatureMap(Interceptor interceptor) {
    Intercepts interceptsAnnotation = interceptor.getClass().getAnnotation(Intercepts.class);
    // issue #251
    if (interceptsAnnotation == null) {
      throw new PluginException("No @Intercepts annotation was found in interceptor " + interceptor.getClass().getName());
    }
    Signature[] sigs = interceptsAnnotation.value();
    Map<Class<?>, Set<Method>> signatureMap = new HashMap<Class<?>, Set<Method>>();
    for (Signature sig : sigs) {
      Set<Method> methods = signatureMap.get(sig.type());
      if (methods == null) {
        methods = new HashSet<Method>();
        signatureMap.put(sig.type(), methods);
      }
      try {
        Method method = sig.type().getMethod(sig.method(), sig.args());
        methods.add(method);
      } catch (NoSuchMethodException e) {
        throw new PluginException("Could not find method on " + sig.type() + " named " + sig.method() + ". Cause: " + e, e);
      }
    }
    return signatureMap;
  }

  private static Class<?>[] getAllInterfaces(Class<?> type, Map<Class<?>, Set<Method>> signatureMap) {
    Set<Class<?>> interfaces = new HashSet<Class<?>>();
    while (type != null) {
      for (Class<?> c : type.getInterfaces()) {
        if (signatureMap.containsKey(c)) {
          interfaces.add(c);
        }
      }
      type = type.getSuperclass();
    }
    return interfaces.toArray(new Class<?>[interfaces.size()]);
  }

}
首先Plugin实现了InvocationHandler 这个接口提供的invoke方法即使真实调用处

通常我们可以在invoke方法根据对应method等等来实现我们的业务逻辑

我们了解到Mybatis的plugin对于符合signature的方法会调用对应interceptor的intercept方法 这也是我们最熟知的业务逻辑

该处会根据签名来匹配合适的方法来使用特定的interceptor

当查找到指定的interceptor会传入invocation

public class Invocation {

  private final Object target;
  private final Method method;
  private final Object[] args;

  public Invocation(Object target, Method method, Object[] args) {
    this.target = target;
    this.method = method;
    this.args = args;
  }

  public Object getTarget() {
    return target;
  }

  public Method getMethod() {
    return method;
  }

  public Object[] getArgs() {
    return args;
  }

  public Object proceed() throws InvocationTargetException, IllegalAccessException {
    return method.invoke(target, args);
  }

}
而invocation的proceed就是调用对应的业务方法【如果还有拦截器将会找到下一个拦截器】===》这边有个前提需要拦截器最终调用invocation.proceed方法！如果不调用，后果很严重！！！

比如我们实现事务超时的mybatis拦截器如下

@Intercepts({
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "batch", args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class})})
public class MybatisTransactionTimeoutInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Statement stmt = (Statement) invocation.getArgs()[0];
        Collection<Object> values = TransactionSynchronizationManager.getResourceMap().values();
        if (!values.isEmpty()) {
            for (Object obj : values) {
                if (obj != null && obj instanceof ConnectionHolder) {
                    ConnectionHolder holder = (ConnectionHolder) obj;
                    if (holder.hasTimeout()) {
                        int queryTimeOut = holder.getTimeToLiveInSeconds();
                        if (stmt.getQueryTimeout() != 0) {
                            queryTimeOut = queryTimeOut < stmt.getQueryTimeout() ? queryTimeOut : stmt.getQueryTimeout();
                        }
                        stmt.setQueryTimeout(queryTimeOut);
                    }
                    break;
                }
            }
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {
        // Do nothing
    }
}

正如我们所说 事实上interceptor的签名匹配思路 因此我们可以对于mybatis插件做如下优化

 @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }
这样可以有效的降低mybatis的代理链嵌套层次 特别是插件太多的情况！

那么我们什么情况才有机会生成动态的插件呢？

Mybatis在启动时会调用InterceptorChain做对应的初始化

public class InterceptorChain {

  private final List<Interceptor> interceptors = new ArrayList<Interceptor>();

  public Object pluginAll(Object target) {
    for (Interceptor interceptor : interceptors) {
      target = interceptor.plugin(target);
    }
    return target;
  }

  public void addInterceptor(Interceptor interceptor) {
    interceptors.add(interceptor);
  }

  public List<Interceptor> getInterceptors() {
    return Collections.unmodifiableList(interceptors);
  }

}



其中有四个地方调用

f6car > MyBatis插件小知识点 > image2017-12-12 11:24:24.png

从上述可知 因此mybatis的插件支持如上四种

parameterHandler
resultSetHandler
statementHandler
executor




