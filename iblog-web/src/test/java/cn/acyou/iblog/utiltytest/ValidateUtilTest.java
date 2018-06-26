package cn.acyou.iblog.utiltytest;

import cn.acyou.iblog.dozer.DozerHelper;
import cn.acyou.iblog.entity.Student;
import cn.acyou.iblog.entity.Teacher;
import cn.acyou.iblog.maintest.BaseTest;
import cn.acyou.iblog.so.ActiveCodeSo;
import cn.acyou.iblog.utility.JsonResult;
import cn.acyou.iblog.utility.ValidateUtil;
import org.junit.Test;

/**
 * @author youfang
 * @version [1.0.0, 2018-06-09 下午 12:37]
 **/
public class ValidateUtilTest extends BaseTest {

    @Test
    public void test1(){
        ActiveCodeSo so = new ActiveCodeSo();
        so.setUsed("1");
        JsonResult result =ValidateUtil.valid(so);
        System.out.println(result);
    }

    @Test
    public void testDozerHelper(){
        Student student = new Student();
        student.setId(1);
        student.setUserName("saa");
        student.setAge(22);
        student.setAddress("nanjing");
        Teacher teacher = new Teacher();
        teacher = DozerHelper.convert(student, Teacher.class);
        System.out.println(teacher);
    }
}
