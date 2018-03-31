package proxytest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author youfang
 * @date 2018-03-31 下午 01:47
 **/
public class WorkHandler implements InvocationHandler{
    private IWork work;

    public WorkHandler(IWork work) {
        this.work = work;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        preWork();
        Object ret = method.invoke(work, args);
        return ret;
    }

    private void preWork() {
        System.out.println("老师工作之前需要先准备课件，讲义，等课前准备");
    }
}
