package avoidnull;

import cn.acyou.iblog.entity.Student;
import org.junit.Test;
import org.springframework.util.Assert;

/**
 * @author youfang
 * @version [1.0.0, 2018-10-30 下午 02:53]
 **/
public class AssertTest {

    @Test
    public void test1(){
        Student student = new Student();
        assert student.getUserName() != null : "用户名不能为空";
        System.out.println("as");
    }

    @Test
    public void test2(){
        Student student = new Student();
        Assert.notNull(student);
        System.out.println("as");
    }
    @Test
    public void test3(){
        Student student = new Student();
        Assert.notNull(student);
        System.out.println("as");
    }
}
