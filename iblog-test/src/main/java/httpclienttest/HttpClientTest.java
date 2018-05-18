package httpclienttest;

import cn.acyou.iblog.utility.HttpClientUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

/**
 * @author youfang
 * @date 2018-04-11 下午 10:28
 **/
public class HttpClientTest {
    @Test
    public void test1() throws Exception{
        //创建一个httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个GET对象
        HttpGet get = new HttpGet("http://www.sogou.com");
        //执行请求
        CloseableHttpResponse response = httpClient.execute(get);
        //取响应的结果
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        HttpEntity entity = response.getEntity();
        String string = EntityUtils.toString(entity, "utf-8");
        System.out.println(string);
        //关闭httpclient
        response.close();
        httpClient.close();
    }

    @Test
    public void test2(){
        String result = HttpClientUtil.doGet("http://f.dataguru.cn/thread-713296-1-1.html");
        System.out.println(result);
    }
    @Test
    public void test21(){
        String appid = "wxd02baafe70a6e8fa";
        String appsecret = "849e3724f3d75fa3b661cd75dd3dfa2a";
        String code = "0117FWzL0rfXG62g1KBL0bEVzL07FWzU";
        String getAccessTokenURL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid + "&secret="+ appsecret +"&code="+ code +"&grant_type=authorization_code";
        String result = HttpClientUtil.doGet(getAccessTokenURL);
        System.out.println(result);
    }

    @Test
    public void test3() throws Exception{
        //创建一个httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个GET对象
        HttpGet get = new HttpGet("http://dev.acyou.cn/api/wx/signature?echostr=234");
        //执行请求
        CloseableHttpResponse response = httpClient.execute(get);
        //取响应的结果
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        HttpEntity entity = response.getEntity();
        String string = EntityUtils.toString(entity, "utf-8");
        System.out.println(string);
        //关闭httpclient
        response.close();
        httpClient.close();
    }
}
