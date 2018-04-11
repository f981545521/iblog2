package ftptest;

import cn.acyou.iblog.utility.FtpUtil;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author youfang
 * @date 2018-04-11 下午 11:33
 **/
public class FtpTest {
    @Test
    public void test1() throws Exception{
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect("192.168.1.116");
        ftpClient.login("ftpuser", "yxserver");
        FileInputStream inputStream = new FileInputStream(new File("D:\\tmp\\400.jpg"));
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        ftpClient.storeFile("123.jpg", inputStream);
        inputStream.close();
        ftpClient.logout();
    }

    @Test
    public void test2() throws Exception{
        FileInputStream in = new FileInputStream(new File("D:\\tmp\\400.jpg"));
        FtpUtil.uploadFile("192.168.1.116", 21, "ftpuser", "yxserver", "/home/ftpuser/","/2018/04/11", "hello.jpg", in);
    }

}
