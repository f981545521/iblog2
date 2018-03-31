package iotest;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件I/O
 * @author youfang
 * @date 2018-03-28 下午 10:09
 **/
public class IoTest {

    @Test
    public void test1() throws IOException {
        File file = new File("G:\\iotest\\123.txt");
        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write("abc".getBytes());
        outputStream.close();
        System.out.println("success");
    }
}
