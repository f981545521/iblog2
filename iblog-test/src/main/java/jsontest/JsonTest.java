package jsontest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.junit.Test;

/**
 * @author youfang
 * @version [1.0.0, 2018-09-30 下午 03:50]
 **/
public class JsonTest {

    @Test
    public void test1(){
        String s = "{\"timestamp\":1538293639626,\"status\":\"000000\",\"message\":\"请求成功\",\"data\":{\"followCount\":0,\"fansCount\":0}}";
        Result result = JSON.parseObject(s, Result.class);
        System.out.println(result);
    }
    @Test
    public void test2(){
        String s = "{\"timestamp\":1538293639626,\"status\":\"000000\",\"message\":\"请求成功\",\"data\":{\"followCount\":0,\"fansCount\":0}}";
        ResultReference<FollowAndFans> resultReference = JSON.parseObject(s, new TypeReference<ResultReference<FollowAndFans>>(){});
        System.out.println(resultReference);
    }
}
