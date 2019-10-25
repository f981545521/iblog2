package cn.acyou.iblog.utility;

import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Base64图片转换工具
 *
 * @author youfang
 * @version [1.0.0, 2019/10/23]
 **/
public class Base64Util {

    public static final String BASE64_PREFIX = "data:image/jpeg;base64,";

    /**
     * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
     *
     * @param imgFile 图片本地路径
     * @return Base64编码
     */
    public static String imageToBase64ByLocal(String imgFile) {
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        //返回Base64编码过的字节数组字符串
        return BASE64_PREFIX + encoder.encode(data);
    }


    /**
     * 在线图片转换成base64字符串
     *
     * @param imgURL 图片线上路径
     * @return Base64编码
     */
    public static String imageToBase64ByOnline(String imgURL) {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        try {
            // 创建URL
            URL url = new URL(imgURL);
            byte[] by = new byte[1024];
            // 创建链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            InputStream is = conn.getInputStream();
            // 将内容读取内存中
            int len = -1;
            while ((len = is.read(by)) != -1) {
                data.write(by, 0, len);
            }
            // 关闭流
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data.toByteArray());
    }


    /**
     * base64字符串转换成图片
     *
     * @param base64Str      base64字符串
     * @param imgFilePath 图片存放路径
     * @return
     */
    public static boolean base64ToImage(String base64Str, String imgFilePath) {
        if (StringUtils.isEmpty(base64Str)){
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] b = decoder.decodeBuffer(base64Str);
/*            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据 ??
                    b[i] += 256;
                }
            }*/
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    /**
     * base64字符串转换成图片
     *
     * @param base64Str      base64字符串
     * @return
     */
    public static ByteArrayInputStream base64ToImage(String base64Str) throws Exception{
        BASE64Decoder decoder = new BASE64Decoder();
        // Base64解码
        byte[] b = decoder.decodeBuffer(base64Str);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(b);
        outputStream.flush();

        return new ByteArrayInputStream(outputStream.toByteArray());
    }


    public static void main(String[] args) throws Exception {
        //本地图片地址
        String url = "C:\\Users\\86180\\Pictures\\小图片\\123.jpg";
        String str = Base64Util.imageToBase64ByLocal(url);
        String substring = str.substring(BASE64_PREFIX.length());
        System.out.println(str);
        System.out.println(substring);
        //String targetUrl = "D:\\poi\\111.jpg";
        //Base64Util.base64ToImage(str, targetUrl);
        //ByteArrayInputStream byteArrayInputStream = Base64Util.base64ToImage(str);
        //System.out.println(byteArrayInputStream);

    }

}
