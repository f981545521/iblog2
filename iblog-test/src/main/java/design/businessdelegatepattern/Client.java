package design.businessdelegatepattern;

/**
 * 客户端
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 11:52]
 **/
public class Client {

    BusinessDelegate businessService;

    public Client(BusinessDelegate businessService){
        this.businessService  = businessService;
    }

    public void doTask(){
        businessService.doTask();
    }
}
