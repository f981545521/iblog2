package proxytest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author youfang
 * @date 2018-03-31 下午 01:46
 **/
public class WorkProxy {
    private IWork work;

    public WorkProxy(IWork work) {
        this.work = work;
    }

    public IWork createWorkProxy(){
        InvocationHandler handler = new WorkHandler(work);
        Class<?>[] iterfaces = new Class[]{IWork.class};
        return (IWork) Proxy.newProxyInstance(IWork.class.getClassLoader(), iterfaces, handler);
    }
}
