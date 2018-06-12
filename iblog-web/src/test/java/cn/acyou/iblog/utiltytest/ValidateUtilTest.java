package cn.acyou.iblog.utiltytest;

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
}
