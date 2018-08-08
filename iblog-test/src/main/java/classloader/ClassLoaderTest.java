package classloader;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-08 上午 11:22]
 **/
public class ClassLoaderTest {

    /**
     * 类装载器就是寻找类的字节码文件，并构造出类在JVM内部表示的对象组件。在Java中，类装载器把一个类装入JVM中，要经过以下步骤：
     *
     * (1) 装载：查找和导入Class文件；
     * (2) 链接：把类的二进制数据合并到JRE中；
     *    (a)校验：检查载入Class文件数据的正确性；
     *    (b)准备：给类的静态变量分配存储空间；
     *    (c)解析：将符号引用转成直接引用；
     * (3) 初始化：对类的静态变量，静态代码块执行初始化操作
     */
    public static void main(String[] args) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        System.out.println(loader);
        System.out.println(loader.getParent());
        System.out.println(loader.getParent().getParent());
    }
}
