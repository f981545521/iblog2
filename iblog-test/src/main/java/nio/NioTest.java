package nio;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * @author youfang
 * @version [1.0.0, 2018-06-23 下午 05:16]
 **/
public class NioTest {

    private static final String filepath = "d:\\tmp\\test.txt";

    @Test
    public void testIntBuffer(){
        IntBuffer buf = IntBuffer.allocate(10) ;
        System.out.println("position = " + buf.position() + "，limit = " + buf.limit() + "，capacty = " + buf.capacity());
        buf.put(6);    // 设置一个数据
        buf.put(16);   // 设置二个数据
        System.out.println("position = " + buf.position() + "，limit = " + buf.limit() + "，capacty = " + buf.capacity());
        buf.flip();
        System.out.println("position = " + buf.position() + "，limit = " + buf.limit() + "，capacty = " + buf.capacity());
    }

    @Test
    public void testwriteFile(){
        String info[] = {"大王","叫我","来","巡山","啊~ ，","我到山上转一转啊！！"} ;
        File file = new File("d:\\tmp" + File.separator + "test.txt") ;
        FileOutputStream output = null ;
        FileChannel fout = null;
        try {
            output = new FileOutputStream(file) ;
            fout = null;
            fout = output.getChannel() ;    // 得到输出的通道
            ByteBuffer buf = ByteBuffer.allocate(1024) ;
            for(int i=0;i<info.length;i++){
                buf.put(info[i].getBytes()) ;   // 字符串变为字节数组放进缓冲区之中
            }
            buf.flip() ;
            fout.write(buf) ;   // 输出缓冲区的内容
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(fout!=null){
                try {
                    fout.close() ;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(output!=null){
                try {
                    output.close() ;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        File file = new File("d:" + File.separator + "test.txt") ;
        FileOutputStream output = null ;
        FileChannel fout = null ;
        try {
            output = new FileOutputStream(file,true) ;
            fout = output.getChannel() ;// 得到通道
            FileLock lock = fout.tryLock() ; // 进行独占锁的操作
            if(lock!=null){
                System.out.println(file.getName() + "文件锁定") ;
                Thread.sleep(5000) ;
                lock.release() ;    // 释放
                System.out.println(file.getName() + "文件解除锁定。") ;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if(fout!=null){
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(output!=null){
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
