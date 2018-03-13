package messytest;

/**
 * GET/SET潜规则
 * 在定义private Boolean isDel;属性后：public Boolean getDel() {..}/public void setDel(Boolean del);
 *
 * 如果属性名以"is"开头，则getter方法会省掉get，set方法会去掉is。例如属性名为isOK，方法是isOK/setOK。
 * 也应该尽量避免属性名的头两个字母中任意一个为大写
 * @author youfang
 * @date 2018-03-12 17:18
 **/
public class GetSetTest {

    private Integer id;
    private String name;
    private String sTudent;//第二个字母大写，生成的get/set并没有将第一个字母大写
    private Boolean isDel;
    private Integer isFirend;
    private String isAsync;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDel() {
        return isDel;
    }

    public void setDel(Boolean del) {
        isDel = del;
    }

    public Integer getIsFirend() {
        return isFirend;
    }

    public void setIsFirend(Integer isFirend) {
        this.isFirend = isFirend;
    }

    public String getsTudent() {
        return sTudent;
    }

    public void setsTudent(String sTudent) {
        this.sTudent = sTudent;
    }

    public String getIsAsync() {
        return isAsync;
    }

    public void setIsAsync(String isAsync) {
        this.isAsync = isAsync;
    }
}
