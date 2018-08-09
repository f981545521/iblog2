package design.businessdelegatepattern;

/**
 * 业务代表。
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 11:52]
 **/
public class BusinessDelegate {
    private BusinessLookUp lookupService = new BusinessLookUp();
    private BusinessService businessService;
    private String serviceType;

    public void setServiceType(String serviceType){
        this.serviceType = serviceType;
    }

    public void doTask(){
        businessService = lookupService.getBusinessService(serviceType);
        businessService.doProcessing();
    }
}
