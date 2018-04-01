package reflecttest;

/**
 * @author youfang
 * @date 2018-04-01 上午 10:41
 **/
public class Teacher {
    private Integer id;
    private String name;

    public void teaching(String course){
        System.out.println("今天上" + course + "课！");
    }
}
