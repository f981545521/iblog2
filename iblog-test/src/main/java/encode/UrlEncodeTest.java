package encode;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Unicode编码
 * @author youfang
 * @version [1.0.0, 2018-06-25 上午 11:59]
 **/
public class UrlEncodeTest {

    @Test
    public void test1() throws UnsupportedEncodingException {
        String code = "eval(\"\\x64\\x6f\\x63\\x75\\x6d\\x65\\x6e\\x74\\x2e\\x77\\x72\\x69\\x74\\x65\\x28\\x27\\u672c\\u7ad9\\u70b9\\u57fa\\u4e8e\\u3010\\u6613\\u65cf\\u667a\\u6c47\\u7f51\\u7edc\\u5546\\u5e97\\u7cfb\\u7edf\\x56\\x34\\x2e\\x30\\u3011\\x28\\u7b80\\u79f0\\x4a\\x61\\x76\\x61\\x73\\x68\\x6f\\x70\\x29\\u5f00\\u53d1\\uff0c\\u4f46\\u672c\\u7ad9\\u70b9\\u672a\\u5f97\\u5230\\u5b98\\u65b9\\u6388\\u6743\\uff0c\\u4e3a\\u975e\\u6cd5\\u7ad9\\u70b9\\u3002\\x3c\\x62\\x72\\x3e\\x4a\\x61\\x76\\x61\\x73\\x68\\x6f\\x70\\u7684\\u5b98\\u65b9\\u7f51\\u7ad9\\u4e3a\\uff1a\\x3c\\x61\\x20\\x68\\x72\\x65\\x66\\x3d\\x22\\x68\\x74\\x74\\x70\\x3a\\x2f\\x2f\\x77\\x77\\x77\\x2e\\x6a\\x61\\x76\\x61\\x6d\\x61\\x6c\\x6c\\x2e\\x63\\x6f\\x6d\\x2e\\x63\\x6e\\x22\\x20\\x74\\x61\\x72\\x67\\x65\\x74\\x3d\\x22\\x5f\\x62\\x6c\\x61\\x6e\\x6b\\x22\\x20\\x3e\\x77\\x77\\x77\\x2e\\x6a\\x61\\x76\\x61\\x6d\\x61\\x6c\\x6c\\x2e\\x63\\x6f\\x6d\\x2e\\x63\\x6e\\x3c\\x2f\\x61\\x3e\\x3c\\x62\\x72\\x3e\\u3010\\u6613\\u65cf\\u667a\\u6c47\\u7f51\\u7edc\\u5546\\u5e97\\u7cfb\\u7edf\\u3011\\u8457\\u4f5c\\u6743\\u5df2\\u5728\\u4e2d\\u534e\\u4eba\\u6c11\\u5171\\u548c\\u56fd\\u56fd\\u5bb6\\u7248\\u6743\\u5c40\\u6ce8\\u518c\\u3002\\x3c\\x62\\x72\\x3e\\u672a\\u7ecf\\u6613\\u65cf\\u667a\\u6c47\\uff08\\u5317\\u4eac\\uff09\\u79d1\\u6280\\u6709\\u9650\\u516c\\u53f8\\u4e66\\u9762\\u6388\\u6743\\uff0c\\x3c\\x62\\x72\\x3e\\u4efb\\u4f55\\u7ec4\\u7ec7\\u6216\\u4e2a\\u4eba\\u4e0d\\u5f97\\u4f7f\\u7528\\uff0c\\x3c\\x62\\x72\\x3e\\u8fdd\\u8005\\u672c\\u516c\\u53f8\\u5c06\\u4f9d\\u6cd5\\u8ffd\\u7a76\\u8d23\\u4efb\\u3002\\x3c\\x62\\x72\\x3e\\x27\\x29\");";
        code = code.replaceAll("\\\\x","%");
        String result = URLDecoder.decode(code, "utf-8");
        //Regex regex = new Regex("/[\\u2E80-\\u2EFF\\u2F00-\\u2FDF\\u3000-\\u303F\\u31C0-\\u31EF\\u3200-\\u32FF\\u3300-\\u33FF\\u3400-\\u4DBF\\u4DC0-\\u4DFF\\u4E00-\\u9FBF\\uF900-\\uFAFF\\uFE30-\\uFE4F\\uFF00-\\uFFEF]+/g");
        //List<String> unicodeList = result.matches("/[\\u2E80-\\u2EFF\\u2F00-\\u2FDF\\u3000-\\u303F\\u31C0-\\u31EF\\u3200-\\u32FF\\u3300-\\u33FF\\u3400-\\u4DBF\\u4DC0-\\u4DFF\\u4E00-\\u9FBF\\uF900-\\uFAFF\\uFE30-\\uFE4F\\uFF00-\\uFFEF]+/g");
        //regex.matcher(result.toCharArray());
        String reg ="\\\\u\\S{4}";
        List<String> resultList = getMatchers(reg, result);
        for (String unicode : resultList){
            result = result.replace(unicode, decodeUnicode(unicode));
        }
        System.out.println(result);
    }

    /**
     * 获取所有满足正则的条件，并生成List
     * @param regex 正则
     * @param source 来源
     * @return 结果集
     */
    List<String> getMatchers(String regex, String source){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(matcher.group());
        }
        return list;
    }

    @Test
    public void test2(){
        String unidodeStr = "\\u5e97\\u7cfb\\u7edf";
        String result = decodeUnicode(unidodeStr);
        System.out.println(result);

    }

    /*
     * 中文转unicode编码
     */
    public static String chineseEncoding(final String gbString) {
        char[] utfBytes = gbString.toCharArray();
        String unicodeBytes = "";
        for (int i = 0; i < utfBytes.length; i++) {
            String hexB = Integer.toHexString(utfBytes[i]);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "\\u" + hexB;
        }
        return unicodeBytes;
    }
    /*
     * unicode编码转中文
     */
    public static String decodeUnicode(final String dataStr) {
        int start = 0;
        int end = 0;
        final StringBuffer buffer = new StringBuffer();
        while (start > -1) {
            end = dataStr.indexOf("\\u", start + 2);
            String charStr = "";
            if (end == -1) {
                charStr = dataStr.substring(start + 2, dataStr.length());
            } else {
                charStr = dataStr.substring(start + 2, end);
            }
            char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
            buffer.append(new Character(letter).toString());
            start = end;
        }
        return buffer.toString();
    }

}
