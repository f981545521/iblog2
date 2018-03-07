背景
动态代理是目前java生态如此繁茂的不可或缺的因素！

比如mybatis中接口实现类的生成 spring aop对于接口的代理实现均是jdk的proxy典型应用！

大量的文档中关于proxy的实现通常都是轻描淡写。比如常见关于cglib aspectj和proxy的讨论

1.jdk是内部生成了一个实现了被代理类所有接口的代理类，用反射来调用InvocationHandler的invoke方法来代理所有类，生成速度最快，执行速度最慢。
2.asm直接操作字节码，然后用classload加载如jvm里，生成速度慢，执行速度较快。
3.感觉cglib是对asm的封装啊，不用了解那么多class类结构就可以用了。由于封装性能比asm差点，但是比JDK快。

4.aspectJ是编译的时候直接编译入切面，速度应该是最快的。

本篇就分析一下proxy的实现。

分析


通常我们提到proxy都是会提到另一个好搭档！InvocationHandler。通常面向切面编程中核心业务都会封装在Invocationhandler中。

/**
 * {@code InvocationHandler} is the interface implemented by
 * the <i>invocation handler</i> of a proxy instance.
 *
 * <p>Each proxy instance has an associated invocation handler.
 * When a method is invoked on a proxy instance, the method
 * invocation is encoded and dispatched to the {@code invoke}
 * method of its invocation handler.
 *
 * @author      Peter Jones
 * @see         Proxy
 * @since       1.3
 */
public interface InvocationHandler {

    /**
     * Processes a method invocation on a proxy instance and returns
     * the result.  This method will be invoked on an invocation handler
     * when a method is invoked on a proxy instance that it is
     * associated with.
     *
     * @param   proxy the proxy instance that the method was invoked on
     *
     * @param   method the {@code Method} instance corresponding to
     * the interface method invoked on the proxy instance.  The declaring
     * class of the {@code Method} object will be the interface that
     * the method was declared in, which may be a superinterface of the
     * proxy interface that the proxy class inherits the method through.
     *
     * @param   args an array of objects containing the values of the
     * arguments passed in the method invocation on the proxy instance,
     * or {@code null} if interface method takes no arguments.
     * Arguments of primitive types are wrapped in instances of the
     * appropriate primitive wrapper class, such as
     * {@code java.lang.Integer} or {@code java.lang.Boolean}.
     *
     * @return  the value to return from the method invocation on the
     * proxy instance.  If the declared return type of the interface
     * method is a primitive type, then the value returned by
     * this method must be an instance of the corresponding primitive
     * wrapper class; otherwise, it must be a type assignable to the
     * declared return type.  If the value returned by this method is
     * {@code null} and the interface method's return type is
     * primitive, then a {@code NullPointerException} will be
     * thrown by the method invocation on the proxy instance.  If the
     * value returned by this method is otherwise not compatible with
     * the interface method's declared return type as described above,
     * a {@code ClassCastException} will be thrown by the method
     * invocation on the proxy instance.
     *
     * @throws  Throwable the exception to throw from the method
     * invocation on the proxy instance.  The exception's type must be
     * assignable either to any of the exception types declared in the
     * {@code throws} clause of the interface method or to the
     * unchecked exception types {@code java.lang.RuntimeException}
     * or {@code java.lang.Error}.  If a checked exception is
     * thrown by this method that is not assignable to any of the
     * exception types declared in the {@code throws} clause of
     * the interface method, then an
     * {@link UndeclaredThrowableException} containing the
     * exception that was thrown by this method will be thrown by the
     * method invocation on the proxy instance.
     *
     * @see     UndeclaredThrowableException
     */
    public Object invoke(Object proxy, Method method, Object[] args)
        throws Throwable;
}



可以看到自从jdk1.3就引入了。该接口只提供了一个高度抽象方法为invoke 通常业务逻辑就是是现在invoke方法中

我们看一下典型的Mybatis的Plugin实现

/**
 *    Copyright 2009-2017 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.plugin;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.reflection.ExceptionUtil;

/**
 * @author Clinton Begin
 */
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

Plugin实现了InvocationHandler接口。通过调用

return Proxy.newProxyInstance(
    type.getClassLoader(),
    interfaces,
    new Plugin(target, interceptor, signatureMap));
返回对应的实例。

我们顺着newProxyInstance查看一下Java是如何实现代理类的生成

/**
 * Returns an instance of a proxy class for the specified interfaces
 * that dispatches method invocations to the specified invocation
 * handler.  This method is equivalent to:
 * <pre>
 *     Proxy.getProxyClass(loader, interfaces).
 *         getConstructor(new Class[] { InvocationHandler.class }).
 *         newInstance(new Object[] { handler });
 * </pre>
 *
 * <p>{@code Proxy.newProxyInstance} throws
 * {@code IllegalArgumentException} for the same reasons that
 * {@code Proxy.getProxyClass} does.
 *
 * @param   loader the class loader to define the proxy class
 * @param   interfaces the list of interfaces for the proxy class
 *          to implement
 * @param   h the invocation handler to dispatch method invocations to
 * @return  a proxy instance with the specified invocation handler of a
 *          proxy class that is defined by the specified class loader
 *          and that implements the specified interfaces
 * @throws  IllegalArgumentException if any of the restrictions on the
 *          parameters that may be passed to {@code getProxyClass}
 *          are violated
 * @throws  NullPointerException if the {@code interfaces} array
 *          argument or any of its elements are {@code null}, or
 *          if the invocation handler, {@code h}, is
 *          {@code null}
 */
public static Object newProxyInstance(ClassLoader loader,
                                      Class<?>[] interfaces,
                                      InvocationHandler h)
    throws IllegalArgumentException
{
    if (h == null) {
        throw new NullPointerException();
    }

    /*
     * Look up or generate the designated proxy class.
     */
    Class<?> cl = getProxyClass0(loader, interfaces); // stack walk magic: do not refactor

    /*
     * Invoke its constructor with the designated invocation handler.
     */
    try {
        final Constructor<?> cons = cl.getConstructor(constructorParams);
        final InvocationHandler ih = h;
        SecurityManager sm = System.getSecurityManager();
        if (sm != null && ProxyAccessHelper.needsNewInstanceCheck(cl)) {
            // create proxy instance with doPrivilege as the proxy class may
            // implement non-public interfaces that requires a special permission
            return AccessController.doPrivileged(new PrivilegedAction<Object>() {
                public Object run() {
                    return newInstance(cons, ih);
                }
            });
        } else {
            return newInstance(cons, ih);
        }
    } catch (NoSuchMethodException e) {
        throw new InternalError(e.toString());
    }
}

很明显核心在于如何根据创建该class

由于我们通常使用jdk代理的时候都是使用接口

那么自然要在生成的类要实现这么多的接口 后面自然可以将生成的实例转成任意上述接口

/*
 * Generate a proxy class (caller-sensitive).
 *
 * To define a proxy class, it performs the access checks as in
 * Class.forName (VM will invoke ClassLoader.checkPackageAccess):
 * 1. "getClassLoader" permission check if loader == null
 * 2. checkPackageAccess on the interfaces it implements
 *
 * To get a constructor and new instance of a proxy class, it performs
 * the package access check on the interfaces it implements
 * as in Class.getConstructor.
 *
 * If an interface is non-public, the proxy class must be defined by
 * the defining loader of the interface.  If the caller's class loader
 * is not the same as the defining loader of the interface, the VM
 * will throw IllegalAccessError when the generated proxy class is
 * being defined via the defineClass0 method.
 */
private static Class<?> getProxyClass0(ClassLoader loader,
                                       Class<?>... interfaces) {
    SecurityManager sm = System.getSecurityManager();
    if (sm != null) {
        final int CALLER_FRAME = 3; // 0: Reflection, 1: getProxyClass0 2: Proxy 3: caller
        final Class<?> caller = Reflection.getCallerClass(CALLER_FRAME);
        final ClassLoader ccl = caller.getClassLoader();
        checkProxyLoader(ccl, loader);
        ReflectUtil.checkProxyPackageAccess(ccl, interfaces);
    }

    if (interfaces.length > 65535) {
        throw new IllegalArgumentException("interface limit exceeded");
    }

    Class<?> proxyClass = null;

    /* collect interface names to use as key for proxy class cache */
    String[] interfaceNames = new String[interfaces.length];

    // for detecting duplicates
    Set<Class<?>> interfaceSet = new HashSet<>();

    for (int i = 0; i < interfaces.length; i++) {
        /*
         * Verify that the class loader resolves the name of this
         * interface to the same Class object.
         */
        String interfaceName = interfaces[i].getName();
        Class<?> interfaceClass = null;
        try {
            interfaceClass = Class.forName(interfaceName, false, loader);
        } catch (ClassNotFoundException e) {
        }
        if (interfaceClass != interfaces[i]) {
            throw new IllegalArgumentException(
                interfaces[i] + " is not visible from class loader");
        }

        /*
         * Verify that the Class object actually represents an
         * interface.
         */
        if (!interfaceClass.isInterface()) {
            throw new IllegalArgumentException(
                interfaceClass.getName() + " is not an interface");
        }

        /*
         * Verify that this interface is not a duplicate.
         */
        if (interfaceSet.contains(interfaceClass)) {
            throw new IllegalArgumentException(
                "repeated interface: " + interfaceClass.getName());
        }
        interfaceSet.add(interfaceClass);

        interfaceNames[i] = interfaceName;
    }

    /*
     * Using string representations of the proxy interfaces as
     * keys in the proxy class cache (instead of their Class
     * objects) is sufficient because we require the proxy
     * interfaces to be resolvable by name through the supplied
     * class loader, and it has the advantage that using a string
     * representation of a class makes for an implicit weak
     * reference to the class.
     */
    List<String> key = Arrays.asList(interfaceNames);

    /*
     * Find or create the proxy class cache for the class loader.
     */
    Map<List<String>, Object> cache;
    synchronized (loaderToCache) {
        cache = loaderToCache.get(loader);
        if (cache == null) {
            cache = new HashMap<>();
            loaderToCache.put(loader, cache);
        }
        /*
         * This mapping will remain valid for the duration of this
         * method, without further synchronization, because the mapping
         * will only be removed if the class loader becomes unreachable.
         */
    }

    /*
     * Look up the list of interfaces in the proxy class cache using
     * the key.  This lookup will result in one of three possible
     * kinds of values:
     *     null, if there is currently no proxy class for the list of
     *         interfaces in the class loader,
     *     the pendingGenerationMarker object, if a proxy class for the
     *         list of interfaces is currently being generated,
     *     or a weak reference to a Class object, if a proxy class for
     *         the list of interfaces has already been generated.
     */
    synchronized (cache) {
        /*
         * Note that we need not worry about reaping the cache for
         * entries with cleared weak references because if a proxy class
         * has been garbage collected, its class loader will have been
         * garbage collected as well, so the entire cache will be reaped
         * from the loaderToCache map.
         */
        do {
            Object value = cache.get(key);
            if (value instanceof Reference) {
                proxyClass = (Class<?>) ((Reference) value).get();
            }
            if (proxyClass != null) {
                // proxy class already generated: return it
                return proxyClass;
            } else if (value == pendingGenerationMarker) {
                // proxy class being generated: wait for it
                try {
                    cache.wait();
                } catch (InterruptedException e) {
                    /*
                     * The class generation that we are waiting for should
                     * take a small, bounded time, so we can safely ignore
                     * thread interrupts here.
                     */
                }
                continue;
            } else {
                /*
                 * No proxy class for this list of interfaces has been
                 * generated or is being generated, so we will go and
                 * generate it now.  Mark it as pending generation.
                 */
                cache.put(key, pendingGenerationMarker);
                break;
            }
        } while (true);
    }

    try {
        String proxyPkg = null;     // package to define proxy class in

        /*
         * Record the package of a non-public proxy interface so that the
         * proxy class will be defined in the same package.  Verify that
         * all non-public proxy interfaces are in the same package.
         */
        for (int i = 0; i < interfaces.length; i++) {
            int flags = interfaces[i].getModifiers();
            if (!Modifier.isPublic(flags)) {
                String name = interfaces[i].getName();
                int n = name.lastIndexOf('.');
                String pkg = ((n == -1) ? "" : name.substring(0, n + 1));
                if (proxyPkg == null) {
                    proxyPkg = pkg;
                } else if (!pkg.equals(proxyPkg)) {
                    throw new IllegalArgumentException(
                        "non-public interfaces from different packages");
                }
            }
        }

        if (proxyPkg == null) {
            // if no non-public proxy interfaces, use com.sun.proxy package
            proxyPkg = ReflectUtil.PROXY_PACKAGE + ".";
        }

        {
            /*
             * Choose a name for the proxy class to generate.
             */
            long num;
            synchronized (nextUniqueNumberLock) {
                num = nextUniqueNumber++;
            }
            String proxyName = proxyPkg + proxyClassNamePrefix + num;
            /*
             * Verify that the class loader hasn't already
             * defined a class with the chosen name.
             */

            /*
             * Generate the specified proxy class.
             */
            byte[] proxyClassFile = ProxyGenerator.generateProxyClass(
                proxyName, interfaces);
            try {
                proxyClass = defineClass0(loader, proxyName,
                    proxyClassFile, 0, proxyClassFile.length);
            } catch (ClassFormatError e) {
                /*
                 * A ClassFormatError here means that (barring bugs in the
                 * proxy class generation code) there was some other
                 * invalid aspect of the arguments supplied to the proxy
                 * class creation (such as virtual machine limitations
                 * exceeded).
                 */
                throw new IllegalArgumentException(e.toString());
            }
        }
        // add to set of all generated proxy classes, for isProxyClass
        proxyClasses.put(proxyClass, null);

    } finally {
        /*
         * We must clean up the "pending generation" state of the proxy
         * class cache entry somehow.  If a proxy class was successfully
         * generated, store it in the cache (with a weak reference);
         * otherwise, remove the reserved entry.  In all cases, notify
         * all waiters on reserved entries in this cache.
         */
        synchronized (cache) {
            if (proxyClass != null) {
                cache.put(key, new WeakReference<Class<?>>(proxyClass));
            } else {
                cache.remove(key);
            }
            cache.notifyAll();
        }
    }
    return proxyClass;
}



当调用getProxyClass0时 首先进行一系列检测 比如接口数量 比如接口是否真的是接口 比如接口是否重复等等

有一个很关键的问题 如果接口并不是public 那么如果想要实现该接口对于实现类来说是有特殊要求的。

比如包名要与对应接口相同才能看到对应接口

由于该代码可能存在并发问题因此在对应使用地方需要加同步

这边有个小技巧 当某个类正在生成是 向map中丢入了 pendingGenerationMarker 后面

当检测到 pendingGenerationMarker时直接执行wait 等待被唤醒即可。

真正生成class的地方为ProxyGenerator.generateProxyClass

我们可以看到

private static final boolean saveGeneratedFiles = (Boolean)AccessController.doPrivileged(new GetBooleanAction("sun.misc.ProxyGenerator.saveGeneratedFiles"));


public static byte[] generateProxyClass(final String var0, Class[] var1) {
    ProxyGenerator var2 = new ProxyGenerator(var0, var1);
    final byte[] var3 = var2.generateClassFile();
    if (saveGeneratedFiles) {
        AccessController.doPrivileged(new PrivilegedAction<Void>() {
            public Void run() {
                try {
                    FileOutputStream var1 = new FileOutputStream(ProxyGenerator.dotToSlash(var0) + ".class");
                    var1.write(var3);
                    var1.close();
                    return null;
                } catch (IOException var2) {
                    throw new InternalError("I/O exception saving generated file: " + var2);
                }
            }
        });
    }

    return var3;
}

可以看到当sun.misc.ProxyGenerator.saveGeneratedFiles为true是会将生成的class文件写入到文件系统中 默认为false

核心生成proxyClass代码如下

private byte[] generateClassFile() {
    this.addProxyMethod(hashCodeMethod, Object.class);
    this.addProxyMethod(equalsMethod, Object.class);
    this.addProxyMethod(toStringMethod, Object.class);

    int var1;
    int var3;
    for(var1 = 0; var1 < this.interfaces.length; ++var1) {
        Method[] var2 = this.interfaces[var1].getMethods();

        for(var3 = 0; var3 < var2.length; ++var3) {
            this.addProxyMethod(var2[var3], this.interfaces[var1]);
        }
    }

    Iterator var7 = this.proxyMethods.values().iterator();

    List var8;
    while(var7.hasNext()) {
        var8 = (List)var7.next();
        checkReturnTypes(var8);
    }

    Iterator var11;
    try {
        this.methods.add(this.generateConstructor());
        var7 = this.proxyMethods.values().iterator();

        while(var7.hasNext()) {
            var8 = (List)var7.next();
            var11 = var8.iterator();

            while(var11.hasNext()) {
                ProxyGenerator.ProxyMethod var4 = (ProxyGenerator.ProxyMethod)var11.next();
                this.fields.add(new ProxyGenerator.FieldInfo(var4.methodFieldName, "Ljava/lang/reflect/Method;", 10));
                this.methods.add(var4.generateMethod());
            }
        }

        this.methods.add(this.generateStaticInitializer());
    } catch (IOException var6) {
        throw new InternalError("unexpected I/O Exception");
    }

    if (this.methods.size() > 65535) {
        throw new IllegalArgumentException("method limit exceeded");
    } else if (this.fields.size() > 65535) {
        throw new IllegalArgumentException("field limit exceeded");
    } else {
        this.cp.getClass(dotToSlash(this.className));
        this.cp.getClass("java/lang/reflect/Proxy");

        for(var1 = 0; var1 < this.interfaces.length; ++var1) {
            this.cp.getClass(dotToSlash(this.interfaces[var1].getName()));
        }

        this.cp.setReadOnly();
        ByteArrayOutputStream var9 = new ByteArrayOutputStream();
        DataOutputStream var10 = new DataOutputStream(var9);

        try {
            var10.writeInt(-889275714);
            var10.writeShort(0);
            var10.writeShort(49);
            this.cp.write(var10);
            var10.writeShort(49);
            var10.writeShort(this.cp.getClass(dotToSlash(this.className)));
            var10.writeShort(this.cp.getClass("java/lang/reflect/Proxy"));
            var10.writeShort(this.interfaces.length);

            for(var3 = 0; var3 < this.interfaces.length; ++var3) {
                var10.writeShort(this.cp.getClass(dotToSlash(this.interfaces[var3].getName())));
            }

            var10.writeShort(this.fields.size());
            var11 = this.fields.iterator();

            while(var11.hasNext()) {
                ProxyGenerator.FieldInfo var12 = (ProxyGenerator.FieldInfo)var11.next();
                var12.write(var10);
            }

            var10.writeShort(this.methods.size());
            var11 = this.methods.iterator();

            while(var11.hasNext()) {
                ProxyGenerator.MethodInfo var13 = (ProxyGenerator.MethodInfo)var11.next();
                var13.write(var10);
            }

            var10.writeShort(0);
            return var9.toByteArray();
        } catch (IOException var5) {
            throw new InternalError("unexpected I/O Exception");
        }
    }
}

我们配上jvm参数-Dsun.misc.ProxyGenerator.saveGeneratedFiles=true

看看我们生成的class吧！

package com.sun.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;
import java.sql.SQLException;
import java.util.List;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.Transaction;

public final class $Proxy159 extends Proxy implements Executor {
    private static Method m1;
    private static Method m5;
    private static Method m2;
    private static Method m12;
    private static Method m8;
    private static Method m9;
    private static Method m10;
    private static Method m0;
    private static Method m15;
    private static Method m14;
    private static Method m3;
    private static Method m6;
    private static Method m17;
    private static Method m16;
    private static Method m4;
    private static Method m7;
    private static Method m13;
    private static Method m11;

    public $Proxy159(InvocationHandler var1) throws  {
        super(var1);
    }

    public final boolean equals(Object var1) throws  {
        try {
            return (Boolean)super.h.invoke(this, m1, new Object[]{var1});
        } catch (RuntimeException | Error var3) {
            throw var3;
        } catch (Throwable var4) {
            throw new UndeclaredThrowableException(var4);
        }
    }

    public final List query(MappedStatement var1, Object var2, RowBounds var3, ResultHandler var4, CacheKey var5, BoundSql var6) throws SQLException {
        try {
            return (List)super.h.invoke(this, m5, new Object[]{var1, var2, var3, var4, var5, var6});
        } catch (RuntimeException | SQLException | Error var8) {
            throw var8;
        } catch (Throwable var9) {
            throw new UndeclaredThrowableException(var9);
        }
    }

    public final String toString() throws  {
        try {
            return (String)super.h.invoke(this, m2, (Object[])null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final CacheKey createCacheKey(MappedStatement var1, Object var2, RowBounds var3, BoundSql var4) throws  {
        try {
            return (CacheKey)super.h.invoke(this, m12, new Object[]{var1, var2, var3, var4});
        } catch (RuntimeException | Error var6) {
            throw var6;
        } catch (Throwable var7) {
            throw new UndeclaredThrowableException(var7);
        }
    }

    public final void rollback(boolean var1) throws SQLException {
        try {
            super.h.invoke(this, m8, new Object[]{var1});
        } catch (RuntimeException | SQLException | Error var3) {
            throw var3;
        } catch (Throwable var4) {
            throw new UndeclaredThrowableException(var4);
        }
    }

    public final boolean isClosed() throws  {
        try {
            return (Boolean)super.h.invoke(this, m9, (Object[])null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final List flushStatements() throws SQLException {
        try {
            return (List)super.h.invoke(this, m10, (Object[])null);
        } catch (RuntimeException | SQLException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final int hashCode() throws  {
        try {
            return (Integer)super.h.invoke(this, m0, (Object[])null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final void deferLoad(MappedStatement var1, MetaObject var2, String var3, CacheKey var4, Class var5) throws  {
        try {
            super.h.invoke(this, m15, new Object[]{var1, var2, var3, var4, var5});
        } catch (RuntimeException | Error var7) {
            throw var7;
        } catch (Throwable var8) {
            throw new UndeclaredThrowableException(var8);
        }
    }

    public final void clearLocalCache() throws  {
        try {
            super.h.invoke(this, m14, (Object[])null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final int update(MappedStatement var1, Object var2) throws SQLException {
        try {
            return (Integer)super.h.invoke(this, m3, new Object[]{var1, var2});
        } catch (RuntimeException | SQLException | Error var4) {
            throw var4;
        } catch (Throwable var5) {
            throw new UndeclaredThrowableException(var5);
        }
    }

    public final List query(MappedStatement var1, Object var2, RowBounds var3, ResultHandler var4) throws SQLException {
        try {
            return (List)super.h.invoke(this, m6, new Object[]{var1, var2, var3, var4});
        } catch (RuntimeException | SQLException | Error var6) {
            throw var6;
        } catch (Throwable var7) {
            throw new UndeclaredThrowableException(var7);
        }
    }

    public final Cursor queryCursor(MappedStatement var1, Object var2, RowBounds var3) throws SQLException {
        try {
            return (Cursor)super.h.invoke(this, m17, new Object[]{var1, var2, var3});
        } catch (RuntimeException | SQLException | Error var5) {
            throw var5;
        } catch (Throwable var6) {
            throw new UndeclaredThrowableException(var6);
        }
    }

    public final void setExecutorWrapper(Executor var1) throws  {
        try {
            super.h.invoke(this, m16, new Object[]{var1});
        } catch (RuntimeException | Error var3) {
            throw var3;
        } catch (Throwable var4) {
            throw new UndeclaredThrowableException(var4);
        }
    }

    public final void close(boolean var1) throws  {
        try {
            super.h.invoke(this, m4, new Object[]{var1});
        } catch (RuntimeException | Error var3) {
            throw var3;
        } catch (Throwable var4) {
            throw new UndeclaredThrowableException(var4);
        }
    }

    public final void commit(boolean var1) throws SQLException {
        try {
            super.h.invoke(this, m7, new Object[]{var1});
        } catch (RuntimeException | SQLException | Error var3) {
            throw var3;
        } catch (Throwable var4) {
            throw new UndeclaredThrowableException(var4);
        }
    }

    public final boolean isCached(MappedStatement var1, CacheKey var2) throws  {
        try {
            return (Boolean)super.h.invoke(this, m13, new Object[]{var1, var2});
        } catch (RuntimeException | Error var4) {
            throw var4;
        } catch (Throwable var5) {
            throw new UndeclaredThrowableException(var5);
        }
    }

    public final Transaction getTransaction() throws  {
        try {
            return (Transaction)super.h.invoke(this, m11, (Object[])null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    static {
        try {
            m1 = Class.forName("java.lang.Object").getMethod("equals", Class.forName("java.lang.Object"));
            m5 = Class.forName("org.apache.ibatis.executor.Executor").getMethod("query", Class.forName("org.apache.ibatis.mapping.MappedStatement"), Class.forName("java.lang.Object"), Class.forName("org.apache.ibatis.session.RowBounds"), Class.forName("org.apache.ibatis.session.ResultHandler"), Class.forName("org.apache.ibatis.cache.CacheKey"), Class.forName("org.apache.ibatis.mapping.BoundSql"));
            m2 = Class.forName("java.lang.Object").getMethod("toString");
            m12 = Class.forName("org.apache.ibatis.executor.Executor").getMethod("createCacheKey", Class.forName("org.apache.ibatis.mapping.MappedStatement"), Class.forName("java.lang.Object"), Class.forName("org.apache.ibatis.session.RowBounds"), Class.forName("org.apache.ibatis.mapping.BoundSql"));
            m8 = Class.forName("org.apache.ibatis.executor.Executor").getMethod("rollback", Boolean.TYPE);
            m9 = Class.forName("org.apache.ibatis.executor.Executor").getMethod("isClosed");
            m10 = Class.forName("org.apache.ibatis.executor.Executor").getMethod("flushStatements");
            m0 = Class.forName("java.lang.Object").getMethod("hashCode");
            m15 = Class.forName("org.apache.ibatis.executor.Executor").getMethod("deferLoad", Class.forName("org.apache.ibatis.mapping.MappedStatement"), Class.forName("org.apache.ibatis.reflection.MetaObject"), Class.forName("java.lang.String"), Class.forName("org.apache.ibatis.cache.CacheKey"), Class.forName("java.lang.Class"));
            m14 = Class.forName("org.apache.ibatis.executor.Executor").getMethod("clearLocalCache");
            m3 = Class.forName("org.apache.ibatis.executor.Executor").getMethod("update", Class.forName("org.apache.ibatis.mapping.MappedStatement"), Class.forName("java.lang.Object"));
            m6 = Class.forName("org.apache.ibatis.executor.Executor").getMethod("query", Class.forName("org.apache.ibatis.mapping.MappedStatement"), Class.forName("java.lang.Object"), Class.forName("org.apache.ibatis.session.RowBounds"), Class.forName("org.apache.ibatis.session.ResultHandler"));
            m17 = Class.forName("org.apache.ibatis.executor.Executor").getMethod("queryCursor", Class.forName("org.apache.ibatis.mapping.MappedStatement"), Class.forName("java.lang.Object"), Class.forName("org.apache.ibatis.session.RowBounds"));
            m16 = Class.forName("org.apache.ibatis.executor.Executor").getMethod("setExecutorWrapper", Class.forName("org.apache.ibatis.executor.Executor"));
            m4 = Class.forName("org.apache.ibatis.executor.Executor").getMethod("close", Boolean.TYPE);
            m7 = Class.forName("org.apache.ibatis.executor.Executor").getMethod("commit", Boolean.TYPE);
            m13 = Class.forName("org.apache.ibatis.executor.Executor").getMethod("isCached", Class.forName("org.apache.ibatis.mapping.MappedStatement"), Class.forName("org.apache.ibatis.cache.CacheKey"));
            m11 = Class.forName("org.apache.ibatis.executor.Executor").getMethod("getTransaction");
        } catch (NoSuchMethodException var2) {
            throw new NoSuchMethodError(var2.getMessage());
        } catch (ClassNotFoundException var3) {
            throw new NoClassDefFoundError(var3.getMessage());
        }
    }
}

代理类都会继承Proxy类【这也是只适合接口的原因 java毕竟不支持多继承】当调用方法时将会找到对应的方法然后通过invokeHandler来实现！【也就是我们的业务逻辑】



这样我们就可以完成代理类的生成！当然由于代理类的增加对于jdk8之前的方法区必然会大大消耗 因此使用各种mybatis插件 spring aop等比较多的项目建议要增大方法区！！！



