package design.businessdelegatepattern;

/**
 * 业务查询服务。
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 11:51]
 **/
public class BusinessLookUp {
    public BusinessService getBusinessService(String serviceType){
        if(serviceType.equalsIgnoreCase("EJB")){
            return new EJBService();
        }else {
            return new JMSService();
        }
    }
}