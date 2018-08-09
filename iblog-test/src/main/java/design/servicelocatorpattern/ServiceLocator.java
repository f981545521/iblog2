package design.servicelocatorpattern;

/**
 * 服务定位器。
 * @author youfang
 * @version [1.0.0, 2018-08-10 上午 12:07]
 **/
public class ServiceLocator {
    private static Cache cache;

    static {
        cache = new Cache();
    }

    public static Service getService(String jndiName){

        Service service = cache.getService(jndiName);

        if(service != null){
            return service;
        }

        InitialContext context = new InitialContext();
        Service service1 = (Service)context.lookup(jndiName);
        cache.addService(service1);
        return service1;
    }
}
