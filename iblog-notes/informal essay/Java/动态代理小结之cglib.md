背景
上一篇简单总结了常见的proxy动态代理小结之proxy

常见我们会使用接口做代理【Proxy】



本次我们查看一下可以针对类DebuggingClassWriter 中的开关

public class DebuggingClassWriter extends ClassVisitor {
    public static final String DEBUG_LOCATION_PROPERTY = "cglib.debugLocation";
    private static String debugLocation = System.getProperty("cglib.debugLocation");
    private static Constructor traceCtor;
    private String className;
    private String superName;

    public DebuggingClassWriter(int flags) {
        super(393216, new ClassWriter(flags));
    }

    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.className = name.replace('/', '.');
        this.superName = superName.replace('/', '.');
        super.visit(version, access, name, signature, superName, interfaces);
    }

    public String getClassName() {
        return this.className;
    }

    public String getSuperName() {
        return this.superName;
    }

    public byte[] toByteArray() {
        return (byte[])((byte[])AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                byte[] b = ((ClassWriter)DebuggingClassWriter.access$001(DebuggingClassWriter.this)).toByteArray();
                if (DebuggingClassWriter.debugLocation != null) {
                    String dirs = DebuggingClassWriter.this.className.replace('.', File.separatorChar);

                    try {
                        (new File(DebuggingClassWriter.debugLocation + File.separatorChar + dirs)).getParentFile().mkdirs();
                        File file = new File(new File(DebuggingClassWriter.debugLocation), dirs + ".class");
                        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));

                        try {
                            out.write(b);
                        } finally {
                            out.close();
                        }

                        if (DebuggingClassWriter.traceCtor != null) {
                            file = new File(new File(DebuggingClassWriter.debugLocation), dirs + ".asm");
                            out = new BufferedOutputStream(new FileOutputStream(file));

                            try {
                                ClassReader cr = new ClassReader(b);
                                PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
                                ClassVisitor tcv = (ClassVisitor)DebuggingClassWriter.traceCtor.newInstance(null, pw);
                                cr.accept(tcv, 0);
                                pw.flush();
                            } finally {
                                out.close();
                            }
                        }
                    } catch (Exception var17) {
                        throw new CodeGenerationException(var17);
                    }
                }

                return b;
            }
        }));
    }

    static {
        if (debugLocation != null) {
            System.err.println("CGLIB debugging enabled, writing to '" + debugLocation + "'");

            try {
                Class clazz = Class.forName("org.springframework.asm.util.TraceClassVisitor");
                traceCtor = clazz.getConstructor(ClassVisitor.class, PrintWriter.class);
            } catch (Throwable var1) {
                ;
            }
        }

    }
}

我们在jvm参数中增加如下

-Dcglib.debugLocation=/Users/qixiaobo/Desktop/cglib


f6car > 动态代理小结之cglib > image2018-1-11 15:7:50.png

这样就能直接生成对应class文件到指定目录 这必然是最简单的研究cglib的方法~

小问题
我们知道Spring开启proxy可以有如下设置

aop:aspectj-autoproxy proxy-target-class="true"
那么当proxy-target-class为true的时候还会使用jdk的代理么？

public class DefaultAopProxyFactory implements AopProxyFactory, Serializable {
    public DefaultAopProxyFactory() {
    }

    public AopProxy createAopProxy(AdvisedSupport config) throws AopConfigException {
        if (!config.isOptimize() && !config.isProxyTargetClass() && !this.hasNoUserSuppliedProxyInterfaces(config)) {
            return new JdkDynamicAopProxy(config);
        } else {
            Class<?> targetClass = config.getTargetClass();
            if (targetClass == null) {
                throw new AopConfigException("TargetSource cannot determine target class: Either an interface or a target is required for proxy creation.");
            } else {
                return (AopProxy)(!targetClass.isInterface() && !Proxy.isProxyClass(targetClass) ? new ObjenesisCglibAopProxy(config) : new JdkDynamicAopProxy(config));
            }
        }
    }

    private boolean hasNoUserSuppliedProxyInterfaces(AdvisedSupport config) {
        Class<?>[] ifcs = config.getProxiedInterfaces();
        return ifcs.length == 0 || ifcs.length == 1 && SpringProxy.class.isAssignableFrom(ifcs[0]);
    }
}

很明显 无论proxytargetClass是否开启 如果targetClass是interface必然会使用动态代理~

分析
找到一个简单的service

public interface CustomPartBrandService {
   /**
    * 新增自定义材料品牌
    * @param tmCustomPartBrand
    * @throws Exception
    */
   void addTmCustomPartBrand(TmCustomPartBrand tmCustomPartBrand) throws Exception;

   /**
    * 获取租户下自定义材料品牌
    * @param idOwnOrg
    * @return
    */
   List<TmCustomPartBrand> getCustomPartBrandListByOrg(String idOwnOrg);
}

其对应的实现如下

@Service("customPartBrandService")
public class CustomPartBrandServiceImpl implements CustomPartBrandService {

   @Autowired
   private CustomPartBrandMapper customPartBrandMapper;
   @Override public void addTmCustomPartBrand(TmCustomPartBrand tmCustomPartBrand) throws Exception{
      int i = customPartBrandMapper.validateCustomerPartBrandName(tmCustomPartBrand.getIdOwnOrg(), tmCustomPartBrand.getPartBrandName());
      if(i >0)
         throw new com.air.tqb.Exception.BussinessException("品牌名称不能重复");

      customPartBrandMapper.addTmCustomPartBrand(tmCustomPartBrand);
   }
   @Override public List<TmCustomPartBrand> getCustomPartBrandListByOrg(String idOwnOrg){
      return customPartBrandMapper.getCustomPartBrandListByOrg(idOwnOrg);
   }
}

而通过cglib之后如下



package com.air.tqb.service.base.impl;

import com.air.tqb.model.TmCustomPartBrand;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.SpringProxy;
import org.springframework.aop.TargetClassAware;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.AopConfigException;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.cglib.core.Signature;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Dispatcher;
import org.springframework.cglib.proxy.Factory;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.cglib.proxy.NoOp;

public class CustomPartBrandServiceImpl$$EnhancerBySpringCGLIB$$949d497f extends CustomPartBrandServiceImpl implements SpringProxy, Advised, Factory {
    private boolean CGLIB$BOUND;
    private boolean CGLIB$CONSTRUCTED;
    private static final ThreadLocal CGLIB$THREAD_CALLBACKS;
    private static final Callback[] CGLIB$STATIC_CALLBACKS;
    private MethodInterceptor CGLIB$CALLBACK_0;
    private MethodInterceptor CGLIB$CALLBACK_1;
    private NoOp CGLIB$CALLBACK_2;
    private Dispatcher CGLIB$CALLBACK_3;
    private Dispatcher CGLIB$CALLBACK_4;
    private MethodInterceptor CGLIB$CALLBACK_5;
    private MethodInterceptor CGLIB$CALLBACK_6;
    private static final Method CGLIB$addTmCustomPartBrand$0$Method;
    private static final MethodProxy CGLIB$addTmCustomPartBrand$0$Proxy;
    private static final Object[] CGLIB$emptyArgs;
    private static final Method CGLIB$getCustomPartBrandListByOrg$1$Method;
    private static final MethodProxy CGLIB$getCustomPartBrandListByOrg$1$Proxy;
    private static final Method CGLIB$equals$3$Method;
    private static final MethodProxy CGLIB$equals$3$Proxy;
    private static final Method CGLIB$toString$4$Method;
    private static final MethodProxy CGLIB$toString$4$Proxy;
    private static final Method CGLIB$hashCode$5$Method;
    private static final MethodProxy CGLIB$hashCode$5$Proxy;
    private static final Method CGLIB$clone$6$Method;
    private static final MethodProxy CGLIB$clone$6$Proxy;

    static void CGLIB$STATICHOOK235() {
        CGLIB$THREAD_CALLBACKS = new ThreadLocal();
        CGLIB$emptyArgs = new Object[0];
        Class var0 = Class.forName("com.air.tqb.service.base.impl.CustomPartBrandServiceImpl$$EnhancerBySpringCGLIB$$949d497f");
        Class var1;
        Method[] var10000 = ReflectUtils.findMethods(new String[]{"addTmCustomPartBrand", "(Lcom/air/tqb/model/TmCustomPartBrand;)V", "getCustomPartBrandListByOrg", "(Ljava/lang/String;)Ljava/util/List;"}, (var1 = Class.forName("com.air.tqb.service.base.impl.CustomPartBrandServiceImpl")).getDeclaredMethods());
        CGLIB$addTmCustomPartBrand$0$Method = var10000[0];
        CGLIB$addTmCustomPartBrand$0$Proxy = MethodProxy.create(var1, var0, "(Lcom/air/tqb/model/TmCustomPartBrand;)V", "addTmCustomPartBrand", "CGLIB$addTmCustomPartBrand$0");
        CGLIB$getCustomPartBrandListByOrg$1$Method = var10000[1];
        CGLIB$getCustomPartBrandListByOrg$1$Proxy = MethodProxy.create(var1, var0, "(Ljava/lang/String;)Ljava/util/List;", "getCustomPartBrandListByOrg", "CGLIB$getCustomPartBrandListByOrg$1");
        var10000 = ReflectUtils.findMethods(new String[]{"equals", "(Ljava/lang/Object;)Z", "toString", "()Ljava/lang/String;", "hashCode", "()I", "clone", "()Ljava/lang/Object;"}, (var1 = Class.forName("java.lang.Object")).getDeclaredMethods());
        CGLIB$equals$3$Method = var10000[0];
        CGLIB$equals$3$Proxy = MethodProxy.create(var1, var0, "(Ljava/lang/Object;)Z", "equals", "CGLIB$equals$3");
        CGLIB$toString$4$Method = var10000[1];
        CGLIB$toString$4$Proxy = MethodProxy.create(var1, var0, "()Ljava/lang/String;", "toString", "CGLIB$toString$4");
        CGLIB$hashCode$5$Method = var10000[2];
        CGLIB$hashCode$5$Proxy = MethodProxy.create(var1, var0, "()I", "hashCode", "CGLIB$hashCode$5");
        CGLIB$clone$6$Method = var10000[3];
        CGLIB$clone$6$Proxy = MethodProxy.create(var1, var0, "()Ljava/lang/Object;", "clone", "CGLIB$clone$6");
    }

    final void CGLIB$addTmCustomPartBrand$0(TmCustomPartBrand var1) throws Exception {
        super.addTmCustomPartBrand(var1);
    }

    public final void addTmCustomPartBrand(TmCustomPartBrand var1) throws Exception {
        try {
            if (!this.CGLIB$CONSTRUCTED) {
                super.addTmCustomPartBrand(var1);
            } else {
                MethodInterceptor var10000 = this.CGLIB$CALLBACK_0;
                if (this.CGLIB$CALLBACK_0 == null) {
                    CGLIB$BIND_CALLBACKS(this);
                    var10000 = this.CGLIB$CALLBACK_0;
                }

                if (var10000 != null) {
                    var10000.intercept(this, CGLIB$addTmCustomPartBrand$0$Method, new Object[]{var1}, CGLIB$addTmCustomPartBrand$0$Proxy);
                } else {
                    super.addTmCustomPartBrand(var1);
                }
            }
        } catch (Error | Exception | RuntimeException var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    final List CGLIB$getCustomPartBrandListByOrg$1(String var1) {
        return super.getCustomPartBrandListByOrg(var1);
    }

    public final List getCustomPartBrandListByOrg(String var1) {
        try {
            if (!this.CGLIB$CONSTRUCTED) {
                return super.getCustomPartBrandListByOrg(var1);
            } else {
                MethodInterceptor var10000 = this.CGLIB$CALLBACK_0;
                if (this.CGLIB$CALLBACK_0 == null) {
                    CGLIB$BIND_CALLBACKS(this);
                    var10000 = this.CGLIB$CALLBACK_0;
                }

                return var10000 != null ? (List)var10000.intercept(this, CGLIB$getCustomPartBrandListByOrg$1$Method, new Object[]{var1}, CGLIB$getCustomPartBrandListByOrg$1$Proxy) : super.getCustomPartBrandListByOrg(var1);
            }
        } catch (Error | RuntimeException var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    final boolean CGLIB$equals$3(Object var1) {
        return super.equals(var1);
    }

    public final boolean equals(Object var1) {
        try {
            if (!this.CGLIB$CONSTRUCTED) {
                return super.equals(var1);
            } else {
                MethodInterceptor var10000 = this.CGLIB$CALLBACK_5;
                if (this.CGLIB$CALLBACK_5 == null) {
                    CGLIB$BIND_CALLBACKS(this);
                    var10000 = this.CGLIB$CALLBACK_5;
                }

                if (var10000 != null) {
                    Object var4 = var10000.intercept(this, CGLIB$equals$3$Method, new Object[]{var1}, CGLIB$equals$3$Proxy);
                    return var4 == null ? false : (Boolean)var4;
                } else {
                    return super.equals(var1);
                }
            }
        } catch (Error | RuntimeException var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    final String CGLIB$toString$4() {
        return super.toString();
    }

    public final String toString() {
        try {
            if (!this.CGLIB$CONSTRUCTED) {
                return super.toString();
            } else {
                MethodInterceptor var10000 = this.CGLIB$CALLBACK_0;
                if (this.CGLIB$CALLBACK_0 == null) {
                    CGLIB$BIND_CALLBACKS(this);
                    var10000 = this.CGLIB$CALLBACK_0;
                }

                return var10000 != null ? (String)var10000.intercept(this, CGLIB$toString$4$Method, CGLIB$emptyArgs, CGLIB$toString$4$Proxy) : super.toString();
            }
        } catch (Error | RuntimeException var1) {
            throw var1;
        } catch (Throwable var2) {
            throw new UndeclaredThrowableException(var2);
        }
    }

    final int CGLIB$hashCode$5() {
        return super.hashCode();
    }

    public final int hashCode() {
        try {
            if (!this.CGLIB$CONSTRUCTED) {
                return super.hashCode();
            } else {
                MethodInterceptor var10000 = this.CGLIB$CALLBACK_6;
                if (this.CGLIB$CALLBACK_6 == null) {
                    CGLIB$BIND_CALLBACKS(this);
                    var10000 = this.CGLIB$CALLBACK_6;
                }

                if (var10000 != null) {
                    Object var3 = var10000.intercept(this, CGLIB$hashCode$5$Method, CGLIB$emptyArgs, CGLIB$hashCode$5$Proxy);
                    return var3 == null ? 0 : ((Number)var3).intValue();
                } else {
                    return super.hashCode();
                }
            }
        } catch (Error | RuntimeException var1) {
            throw var1;
        } catch (Throwable var2) {
            throw new UndeclaredThrowableException(var2);
        }
    }

    final Object CGLIB$clone$6() throws CloneNotSupportedException {
        return super.clone();
    }

    protected final Object clone() throws CloneNotSupportedException {
        try {
            if (!this.CGLIB$CONSTRUCTED) {
                return super.clone();
            } else {
                MethodInterceptor var10000 = this.CGLIB$CALLBACK_0;
                if (this.CGLIB$CALLBACK_0 == null) {
                    CGLIB$BIND_CALLBACKS(this);
                    var10000 = this.CGLIB$CALLBACK_0;
                }

                return var10000 != null ? var10000.intercept(this, CGLIB$clone$6$Method, CGLIB$emptyArgs, CGLIB$clone$6$Proxy) : super.clone();
            }
        } catch (Error | CloneNotSupportedException | RuntimeException var1) {
            throw var1;
        } catch (Throwable var2) {
            throw new UndeclaredThrowableException(var2);
        }
    }

    public static MethodProxy CGLIB$findMethodProxy(Signature var0) {
        String var10000 = var0.toString();
        switch(var10000.hashCode()) {
        case -508378822:
            if (var10000.equals("clone()Ljava/lang/Object;")) {
                return CGLIB$clone$6$Proxy;
            }
            break;
        case -417273782:
            if (var10000.equals("getCustomPartBrandListByOrg(Ljava/lang/String;)Ljava/util/List;")) {
                return CGLIB$getCustomPartBrandListByOrg$1$Proxy;
            }
            break;
        case 128767950:
            if (var10000.equals("addTmCustomPartBrand(Lcom/air/tqb/model/TmCustomPartBrand;)V")) {
                return CGLIB$addTmCustomPartBrand$0$Proxy;
            }
            break;
        case 1826985398:
            if (var10000.equals("equals(Ljava/lang/Object;)Z")) {
                return CGLIB$equals$3$Proxy;
            }
            break;
        case 1913648695:
            if (var10000.equals("toString()Ljava/lang/String;")) {
                return CGLIB$toString$4$Proxy;
            }
            break;
        case 1984935277:
            if (var10000.equals("hashCode()I")) {
                return CGLIB$hashCode$5$Proxy;
            }
        }

        return null;
    }

    public final int indexOf(Advisor var1) {
        try {
            if (!this.CGLIB$CONSTRUCTED) {
                return super.indexOf(var1);
            } else {
                Dispatcher var10000 = this.CGLIB$CALLBACK_4;
                if (this.CGLIB$CALLBACK_4 == null) {
                    CGLIB$BIND_CALLBACKS(this);
                    var10000 = this.CGLIB$CALLBACK_4;
                }

                return ((Advised)var10000.loadObject()).indexOf(var1);
            }
        } catch (Error | RuntimeException var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final int indexOf(Advice var1) {
        try {
            if (!this.CGLIB$CONSTRUCTED) {
                return super.indexOf(var1);
            } else {
                Dispatcher var10000 = this.CGLIB$CALLBACK_4;
                if (this.CGLIB$CALLBACK_4 == null) {
                    CGLIB$BIND_CALLBACKS(this);
                    var10000 = this.CGLIB$CALLBACK_4;
                }

                return ((Advised)var10000.loadObject()).indexOf(var1);
            }
        } catch (Error | RuntimeException var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final boolean isFrozen() {
        try {
            if (!this.CGLIB$CONSTRUCTED) {
                return super.isFrozen();
            } else {
                Dispatcher var10000 = this.CGLIB$CALLBACK_4;
                if (this.CGLIB$CALLBACK_4 == null) {
                    CGLIB$BIND_CALLBACKS(this);
                    var10000 = this.CGLIB$CALLBACK_4;
                }

                return ((Advised)var10000.loadObject()).isFrozen();
            }
        } catch (Error | RuntimeException var1) {
            throw var1;
        } catch (Throwable var2) {
            throw new UndeclaredThrowableException(var2);
        }
    }

    public final void setExposeProxy(boolean var1) {
        try {
            if (!this.CGLIB$CONSTRUCTED) {
                super.setExposeProxy(var1);
            } else {
                Dispatcher var10000 = this.CGLIB$CALLBACK_4;
                if (this.CGLIB$CALLBACK_4 == null) {
                    CGLIB$BIND_CALLBACKS(this);
                    var10000 = this.CGLIB$CALLBACK_4;
                }

                ((Advised)var10000.loadObject()).setExposeProxy(var1);
            }
        } catch (Error | RuntimeException var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final boolean isExposeProxy() {
        try {
            if (!this.CGLIB$CONSTRUCTED) {
                return super.isExposeProxy();
            } else {
                Dispatcher var10000 = this.CGLIB$CALLBACK_4;
                if (this.CGLIB$CALLBACK_4 == null) {
                    CGLIB$BIND_CALLBACKS(this);
                    var10000 = this.CGLIB$CALLBACK_4;
                }

                return ((Advised)var10000.loadObject()).isExposeProxy();
            }
        } catch (Error | RuntimeException var1) {
            throw var1;
        } catch (Throwable var2) {
            throw new UndeclaredThrowableException(var2);
        }
    }

    public final TargetSource getTargetSource() {
        try {
            if (!this.CGLIB$CONSTRUCTED) {
                return super.getTargetSource();
            } else {
                Dispatcher var10000 = this.CGLIB$CALLBACK_4;
                if (this.CGLIB$CALLBACK_4 == null) {
                    CGLIB$BIND_CALLBACKS(this);
                    var10000 = this.CGLIB$CALLBACK_4;
                }

                return ((Advised)var10000.loadObject()).getTargetSource();
            }
        } catch (Error | RuntimeException var1) {
            throw var1;
        } catch (Throwable var2) {
            throw new UndeclaredThrowableException(var2);
        }
    }

    public final void addAdvisor(int var1, Advisor var2) throws AopConfigException {
        try {
            if (!this.CGLIB$CONSTRUCTED) {
                super.addAdvisor(var1, var2);
            } else {
                Dispatcher var10000 = this.CGLIB$CALLBACK_4;
                if (this.CGLIB$CALLBACK_4 == null) {
                    CGLIB$BIND_CALLBACKS(this);
                    var10000 = this.CGLIB$CALLBACK_4;
                }

                ((Advised)var10000.loadObject()).addAdvisor(var1, var2);
            }
        } catch (Error | AopConfigException | RuntimeException var3) {
            throw var3;
        } catch (Throwable var4) {
            throw new UndeclaredThrowableException(var4);
        }
    }

    public final void addAdvisor(Advisor var1) throws AopConfigException {
        try {
            if (!this.CGLIB$CONSTRUCTED) {
                super.addAdvisor(var1);
            } else {
                Dispatcher var10000 = this.CGLIB$CALLBACK_4;
                if (this.CGLIB$CALLBACK_4 == null) {
                    CGLIB$BIND_CALLBACKS(this);
                    var10000 = this.CGLIB$CALLBACK_4;
                }

                ((Advised)var10000.loadObject()).addAdvisor(var1);
            }
        } catch (Error | AopConfigException | RuntimeException var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final void setTargetSource(TargetSource var1) {
        try {
            if (!this.CGLIB$CONSTRUCTED) {
                super.setTargetSource(var1);
            } else {
                Dispatcher var10000 = this.CGLIB$CALLBACK_4;
                if (this.CGLIB$CALLBACK_4 == null) {
                    CGLIB$BIND_CALLBACKS(this);
                    var10000 = this.CGLIB$CALLBACK_4;
                }

                ((Advised)var10000.loadObject()).setTargetSource(var1);
            }
        } catch (Error | RuntimeException var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final void setPreFiltered(boolean var1) {
        try {
            if (!this.CGLIB$CONSTRUCTED) {
                super.setPreFiltered(var1);
            } else {
                Dispatcher var10000 = this.CGLIB$CALLBACK_4;
                if (this.CGLIB$CALLBACK_4 == null) {
                    CGLIB$BIND_CALLBACKS(this);
                    var10000 = this.CGLIB$CALLBACK_4;
                }

                ((Advised)var10000.loadObject()).setPreFiltered(var1);
            }
        } catch (Error | RuntimeException var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final boolean isProxyTargetClass() {
        try {
            if (!this.CGLIB$CONSTRUCTED) {
                return super.isProxyTargetClass();
            } else {
                Dispatcher var10000 = this.CGLIB$CALLBACK_4;
                if (this.CGLIB$CALLBACK_4 == null) {
                    CGLIB$BIND_CALLBACKS(this);
                    var10000 = this.CGLIB$CALLBACK_4;
                }

                return ((Advised)var10000.loadObject()).isProxyTargetClass();
            }
        } catch (Error | RuntimeException var1) {
            throw var1;
        } catch (Throwable var2) {
            throw new UndeclaredThrowableException(var2);
        }
    }

    public final Advisor[] getAdvisors() {
        try {
            if (!this.CGLIB$CONSTRUCTED) {
                return super.getAdvisors();
            } else {
                Dispatcher var10000 = this.CGLIB$CALLBACK_4;
                if (this.CGLIB$CALLBACK_4 == null) {
                    CGLIB$BIND_CALLBACKS(this);
                    var10000 = this.CGLIB$CALLBACK_4;
                }

                return ((Advised)var10000.loadObject()).getAdvisors();
            }
        } catch (Error | RuntimeException var1) {
            throw var1;
        } catch (Throwable var2) {
            throw new UndeclaredThrowableException(var2);
        }
    }

    public final void addAdvice(Advice var1) throws AopConfigException {
        try {
            if (!this.CGLIB$CONSTRUCTED) {
                super.addAdvice(var1);
            } else {
                Dispatcher var10000 = this.CGLIB$CALLBACK_4;
                if (this.CGLIB$CALLBACK_4 == null) {
                    CGLIB$BIND_CALLBACKS(this);
                    var10000 = this.CGLIB$CALLBACK_4;
                }

                ((Advised)var10000.loadObject()).addAdvice(var1);
            }
        } catch (Error | AopConfigException | RuntimeException var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final void addAdvice(int var1, Advice var2) throws AopConfigException {
        try {
            if (!this.CGLIB$CONSTRUCTED) {
                super.addAdvice(var1, var2);
            } else {
                Dispatcher var10000 = this.CGLIB$CALLBACK_4;
                if (this.CGLIB$CALLBACK_4 == null) {
                    CGLIB$BIND_CALLBACKS(this);
                    var10000 = this.CGLIB$CALLBACK_4;
                }

                ((Advised)var10000.loadObject()).addAdvice(var1, var2);
            }
        } catch (Error | AopConfigException | RuntimeException var3) {
            throw var3;
        } catch (Throwable var4) {
            throw new UndeclaredThrowableException(var4);
        }
    }

    public final boolean isPreFiltered() {
        try {
            if (!this.CGLIB$CONSTRUCTED) {
                return super.isPreFiltered();
            } else {
                Dispatcher var10000 = this.CGLIB$CALLBACK_4;
                if (this.CGLIB$CALLBACK_4 == null) {
                    CGLIB$BIND_CALLBACKS(this);
                    var10000 = this.CGLIB$CALLBACK_4;
                }

                return ((Advised)var10000.loadObject()).isPreFiltered();
            }
        } catch (Error | RuntimeException var1) {
            throw var1;
        } catch (Throwable var2) {
            throw new UndeclaredThrowableException(var2);
        }
    }

    public final Class[] getProxiedInterfaces() {
        try {
            if (!this.CGLIB$CONSTRUCTED) {
                return super.getProxiedInterfaces();
            } else {
                Dispatcher var10000 = this.CGLIB$CALLBACK_4;
                if (this.CGLIB$CALLBACK_4 == null) {
                    CGLIB$BIND_CALLBACKS(this);
                    var10000 = this.CGLIB$CALLBACK_4;
                }

                return ((Advised)var10000.loadObject()).getProxiedInterfaces();
            }
        } catch (Error | RuntimeException var1) {
            throw var1;
        } catch (Throwable var2) {
            throw new UndeclaredThrowableException(var2);
        }
    }

    public final boolean isInterfaceProxied(Class var1) {
        try {
            if (!this.CGLIB$CONSTRUCTED) {
                return super.isInterfaceProxied(var1);
            } else {
                Dispatcher var10000 = this.CGLIB$CALLBACK_4;
                if (this.CGLIB$CALLBACK_4 == null) {
                    CGLIB$BIND_CALLBACKS(this);
                    var10000 = this.CGLIB$CALLBACK_4;
                }

                return ((Advised)var10000.loadObject()).isInterfaceProxied(var1);
            }
        } catch (Error | RuntimeException var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final boolean removeAdvisor(Advisor var1) {
        try {
            if (!this.CGLIB$CONSTRUCTED) {
                return super.removeAdvisor(var1);
            } else {
                Dispatcher var10000 = this.CGLIB$CALLBACK_4;
                if (this.CGLIB$CALLBACK_4 == null) {
                    CGLIB$BIND_CALLBACKS(this);
                    var10000 = this.CGLIB$CALLBACK_4;
                }

                return ((Advised)var10000.loadObject()).removeAdvisor(var1);
            }
        } catch (Error | RuntimeException var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final void removeAdvisor(int var1) throws AopConfigException {
        try {
            if (!this.CGLIB$CONSTRUCTED) {
                super.removeAdvisor(var1);
            } else {
                Dispatcher var10000 = this.CGLIB$CALLBACK_4;
                if (this.CGLIB$CALLBACK_4 == null) {
                    CGLIB$BIND_CALLBACKS(this);
                    var10000 = this.CGLIB$CALLBACK_4;
                }

                ((Advised)var10000.loadObject()).removeAdvisor(var1);
            }
        } catch (Error | AopConfigException | RuntimeException var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final boolean replaceAdvisor(Advisor var1, Advisor var2) throws AopConfigException {
        try {
            if (!this.CGLIB$CONSTRUCTED) {
                return super.replaceAdvisor(var1, var2);
            } else {
                Dispatcher var10000 = this.CGLIB$CALLBACK_4;
                if (this.CGLIB$CALLBACK_4 == null) {
                    CGLIB$BIND_CALLBACKS(this);
                    var10000 = this.CGLIB$CALLBACK_4;
                }

                return ((Advised)var10000.loadObject()).replaceAdvisor(var1, var2);
            }
        } catch (Error | AopConfigException | RuntimeException var3) {
            throw var3;
        } catch (Throwable var4) {
            throw new UndeclaredThrowableException(var4);
        }
    }

    public final boolean removeAdvice(Advice var1) {
        try {
            if (!this.CGLIB$CONSTRUCTED) {
                return super.removeAdvice(var1);
            } else {
                Dispatcher var10000 = this.CGLIB$CALLBACK_4;
                if (this.CGLIB$CALLBACK_4 == null) {
                    CGLIB$BIND_CALLBACKS(this);
                    var10000 = this.CGLIB$CALLBACK_4;
                }

                return ((Advised)var10000.loadObject()).removeAdvice(var1);
            }
        } catch (Error | RuntimeException var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final String toProxyConfigString() {
        try {
            if (!this.CGLIB$CONSTRUCTED) {
                return super.toProxyConfigString();
            } else {
                Dispatcher var10000 = this.CGLIB$CALLBACK_4;
                if (this.CGLIB$CALLBACK_4 == null) {
                    CGLIB$BIND_CALLBACKS(this);
                    var10000 = this.CGLIB$CALLBACK_4;
                }

                return ((Advised)var10000.loadObject()).toProxyConfigString();
            }
        } catch (Error | RuntimeException var1) {
            throw var1;
        } catch (Throwable var2) {
            throw new UndeclaredThrowableException(var2);
        }
    }

    public final Class getTargetClass() {
        try {
            if (!this.CGLIB$CONSTRUCTED) {
                return super.getTargetClass();
            } else {
                Dispatcher var10000 = this.CGLIB$CALLBACK_4;
                if (this.CGLIB$CALLBACK_4 == null) {
                    CGLIB$BIND_CALLBACKS(this);
                    var10000 = this.CGLIB$CALLBACK_4;
                }

                return ((TargetClassAware)var10000.loadObject()).getTargetClass();
            }
        } catch (Error | RuntimeException var1) {
            throw var1;
        } catch (Throwable var2) {
            throw new UndeclaredThrowableException(var2);
        }
    }

    public CustomPartBrandServiceImpl$$EnhancerBySpringCGLIB$$949d497f() {
        try {
            super();
            CGLIB$BIND_CALLBACKS(this);
            this.CGLIB$CONSTRUCTED = true;
        } catch (Error | RuntimeException var1) {
            throw var1;
        } catch (Throwable var2) {
            throw new UndeclaredThrowableException(var2);
        }
    }

    public static void CGLIB$SET_THREAD_CALLBACKS(Callback[] var0) {
        CGLIB$THREAD_CALLBACKS.set(var0);
    }

    public static void CGLIB$SET_STATIC_CALLBACKS(Callback[] var0) {
        CGLIB$STATIC_CALLBACKS = var0;
    }

    private static final void CGLIB$BIND_CALLBACKS(Object var0) {
        CustomPartBrandServiceImpl$$EnhancerBySpringCGLIB$$949d497f var1 = (CustomPartBrandServiceImpl$$EnhancerBySpringCGLIB$$949d497f)var0;
        if (!var1.CGLIB$BOUND) {
            var1.CGLIB$BOUND = true;
            Object var10000 = CGLIB$THREAD_CALLBACKS.get();
            if (var10000 == null) {
                var10000 = CGLIB$STATIC_CALLBACKS;
                if (CGLIB$STATIC_CALLBACKS == null) {
                    return;
                }
            }

            Callback[] var10001 = (Callback[])var10000;
            var1.CGLIB$CALLBACK_6 = (MethodInterceptor)((Callback[])var10000)[6];
            var1.CGLIB$CALLBACK_5 = (MethodInterceptor)var10001[5];
            var1.CGLIB$CALLBACK_4 = (Dispatcher)var10001[4];
            var1.CGLIB$CALLBACK_3 = (Dispatcher)var10001[3];
            var1.CGLIB$CALLBACK_2 = (NoOp)var10001[2];
            var1.CGLIB$CALLBACK_1 = (MethodInterceptor)var10001[1];
            var1.CGLIB$CALLBACK_0 = (MethodInterceptor)var10001[0];
        }

    }

    public Object newInstance(Callback[] var1) {
        try {
            CGLIB$SET_THREAD_CALLBACKS(var1);
            CustomPartBrandServiceImpl$$EnhancerBySpringCGLIB$$949d497f var10000 = new CustomPartBrandServiceImpl$$EnhancerBySpringCGLIB$$949d497f();
            CGLIB$SET_THREAD_CALLBACKS((Callback[])null);
            return var10000;
        } catch (Error | RuntimeException var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public Object newInstance(Callback var1) {
        try {
            throw new IllegalStateException("More than one callback object required");
        } catch (Error | RuntimeException var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public Object newInstance(Class[] var1, Object[] var2, Callback[] var3) {
        try {
            CGLIB$SET_THREAD_CALLBACKS(var3);
            CustomPartBrandServiceImpl$$EnhancerBySpringCGLIB$$949d497f var10000 = new CustomPartBrandServiceImpl$$EnhancerBySpringCGLIB$$949d497f;
            switch(var1.length) {
            case 0:
                var10000.<init>();
                CGLIB$SET_THREAD_CALLBACKS((Callback[])null);
                return var10000;
            default:
                throw new IllegalArgumentException("Constructor not found");
            }
        } catch (Error | RuntimeException var4) {
            throw var4;
        } catch (Throwable var5) {
            throw new UndeclaredThrowableException(var5);
        }
    }

    public Callback getCallback(int var1) {
        try {
            CGLIB$BIND_CALLBACKS(this);
            Object var10000;
            switch(var1) {
            case 0:
                var10000 = this.CGLIB$CALLBACK_0;
                break;
            case 1:
                var10000 = this.CGLIB$CALLBACK_1;
                break;
            case 2:
                var10000 = this.CGLIB$CALLBACK_2;
                break;
            case 3:
                var10000 = this.CGLIB$CALLBACK_3;
                break;
            case 4:
                var10000 = this.CGLIB$CALLBACK_4;
                break;
            case 5:
                var10000 = this.CGLIB$CALLBACK_5;
                break;
            case 6:
                var10000 = this.CGLIB$CALLBACK_6;
                break;
            default:
                var10000 = null;
            }

            return (Callback)var10000;
        } catch (Error | RuntimeException var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public void setCallback(int var1, Callback var2) {
        try {
            switch(var1) {
            case 0:
                this.CGLIB$CALLBACK_0 = (MethodInterceptor)var2;
                break;
            case 1:
                this.CGLIB$CALLBACK_1 = (MethodInterceptor)var2;
                break;
            case 2:
                this.CGLIB$CALLBACK_2 = (NoOp)var2;
                break;
            case 3:
                this.CGLIB$CALLBACK_3 = (Dispatcher)var2;
                break;
            case 4:
                this.CGLIB$CALLBACK_4 = (Dispatcher)var2;
                break;
            case 5:
                this.CGLIB$CALLBACK_5 = (MethodInterceptor)var2;
                break;
            case 6:
                this.CGLIB$CALLBACK_6 = (MethodInterceptor)var2;
            }

        } catch (Error | RuntimeException var3) {
            throw var3;
        } catch (Throwable var4) {
            throw new UndeclaredThrowableException(var4);
        }
    }

    public Callback[] getCallbacks() {
        try {
            CGLIB$BIND_CALLBACKS(this);
            return new Callback[]{this.CGLIB$CALLBACK_0, this.CGLIB$CALLBACK_1, this.CGLIB$CALLBACK_2, this.CGLIB$CALLBACK_3, this.CGLIB$CALLBACK_4, this.CGLIB$CALLBACK_5, this.CGLIB$CALLBACK_6};
        } catch (Error | RuntimeException var1) {
            throw var1;
        } catch (Throwable var2) {
            throw new UndeclaredThrowableException(var2);
        }
    }

    public void setCallbacks(Callback[] var1) {
        try {
            this.CGLIB$CALLBACK_0 = (MethodInterceptor)var1[0];
            this.CGLIB$CALLBACK_1 = (MethodInterceptor)var1[1];
            this.CGLIB$CALLBACK_2 = (NoOp)var1[2];
            this.CGLIB$CALLBACK_3 = (Dispatcher)var1[3];
            this.CGLIB$CALLBACK_4 = (Dispatcher)var1[4];
            this.CGLIB$CALLBACK_5 = (MethodInterceptor)var1[5];
            this.CGLIB$CALLBACK_6 = (MethodInterceptor)var1[6];
        } catch (Error | RuntimeException var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    static {
        CGLIB$STATICHOOK236();
        CGLIB$STATICHOOK235();
    }

    static void CGLIB$STATICHOOK236() {
        try {
            ;
        } catch (Error | RuntimeException var0) {
            throw var0;
        } catch (Throwable var1) {
            throw new UndeclaredThrowableException(var1);
        }
    }
}





可以看到确实增强了许多方法 而这个时候我们很容易就发现spring aop内部嵌套调用 这个问题了  因为当实际方法调用的时候如下

public final List getCustomPartBrandListByOrg(String var1) {
    try {
        if (!this.CGLIB$CONSTRUCTED) {
            return super.getCustomPartBrandListByOrg(var1);
        } else {
            MethodInterceptor var10000 = this.CGLIB$CALLBACK_0;
            if (this.CGLIB$CALLBACK_0 == null) {
                CGLIB$BIND_CALLBACKS(this);
                var10000 = this.CGLIB$CALLBACK_0;
            }

            return var10000 != null ? (List)var10000.intercept(this, CGLIB$getCustomPartBrandListByOrg$1$Method, new Object[]{var1}, CGLIB$getCustomPartBrandListByOrg$1$Proxy) : super.getCustomPartBrandListByOrg(var1);
        }
    } catch (Error | RuntimeException var2) {
        throw var2;
    } catch (Throwable var3) {
        throw new UndeclaredThrowableException(var3);
    }
}
只有当CGLIB$CONSTRUCTED为false或者无法找到对应的callback是才会调用super 那么由于多态的原因 当存在嵌套调用是能够调用到子类的

否则是执行intercept方法 intercept方法我们也曾确认过 真正调用的对象变成了未被代理的对象~

//TODO

