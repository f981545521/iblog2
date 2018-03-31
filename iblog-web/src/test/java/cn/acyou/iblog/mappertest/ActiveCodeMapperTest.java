package cn.acyou.iblog.mappertest;

import cn.acyou.iblog.maintest.BaseTest;
import cn.acyou.iblog.mappers.ActiveCodeMapper;
import cn.acyou.iblog.so.ActiveCodeSo;
import org.junit.Before;
import org.junit.Test;


/**
 * @author youfang
 * @date 2018-03-31 下午 02:21
 **/
public class ActiveCodeMapperTest extends BaseTest {
    private ActiveCodeMapper activeCodeMapper;

    @Before
    public void initTest(){
        activeCodeMapper = applicationContext.getBean("activeCodeMapper",ActiveCodeMapper.class);
    }

    @Test
    public void test1(){
        ActiveCodeSo activeCodeSo = new ActiveCodeSo();
        activeCodeSo.setEmail("youfang@acyou.cn");
        activeCodeSo.setUsed("y");
        System.out.println(activeCodeMapper.findActiveCodeBySo(activeCodeSo));
    }
}
