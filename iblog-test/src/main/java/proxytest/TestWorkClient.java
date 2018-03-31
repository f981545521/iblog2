package proxytest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author youfang
 * @date 2018-03-31 下午 01:49
 **/
public class TestWorkClient {
    public static void main(String[] args) throws Exception {
        IWork teachwork = new TeacherWoke();
        WorkProxy workproxy = new WorkProxy(teachwork);
        IWork work = workproxy.createWorkProxy();
        work.work();

        /**
         * 官网
         */
        IWork teachwork2 = new TeacherWoke();
        InvocationHandler handler = new WorkHandler(teachwork2);
        Class proxyClass = Proxy.getProxyClass(IWork.class.getClassLoader(), new Class[] { IWork.class });
        IWork work2 = (IWork) proxyClass.getConstructor(new Class[] { InvocationHandler.class }).newInstance(new Object[] { handler });
        work2.work();
    }
}
