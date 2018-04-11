package linuxtest;

import com.jcraft.jsch.JSchException;
import org.junit.Test;

import java.io.IOException;

/**
 * @author youfang
 * @date 2018-04-11 下午 09:38
 *
 * Java 使用 jsch 连接Linux服务器，执行Linux命令。
 **/
public class LinuxTest {

    @Test
    public void test1() throws IOException, InterruptedException {
        String command = "mkdir /test";
        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();
        System.out.println("test 文件夹创建成功！");
    }

    @Test
    public void test2() throws Exception {
        SSHHelper helper = new SSHHelper("192.168.1.116", 22, "root", "yxserver");
        String command = "rm -rf /test";
        SSHResInfo resInfo = helper.sendCmd(command);
        System.out.println(resInfo.toString());
        helper.close();
    }
}
