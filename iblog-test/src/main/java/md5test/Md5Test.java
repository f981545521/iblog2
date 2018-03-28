package md5test;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

/**
 * @author youfang
 * @date 2018-03-28 下午 05:10
 **/
public class Md5Test {
    @Test
    public void test1(){
        //使用md5加密，没有解密的方式；不过简单的密码md5可以用一个叫彩虹表的方式暴力破解。
        //比如123456的md5值很容易在网上找到明文，给它加上一个特定的盐值后是很难破解的。
        String md5 = DigestUtils.md5Hex("123456");
        System.out.println(md5);
        String md52 = DigestUtils.md5Hex("小星星" + "123456");
        System.out.println(md52);
    }

}
