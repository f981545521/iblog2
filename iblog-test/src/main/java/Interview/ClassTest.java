package Interview;

/**
 * @author youfang
 * @date 2018-03-24 下午 07:47
 * 一个文件中可以存在多个类。但是只能有一个public类，并且此public类必须与文件名相同
 * 编译的时候内部类会编译成：ClassTest$Foo.class 匿名内部类：ClassTest$1.class
 * Aoo Boo 则会编译成独立的class文件。
 **/
public class ClassTest {

    class Foo{
        void sayHi(){
            System.out.println("hi");
        }
    }

    Thread thread = new Thread() {
        public void run() {
            for (int i = 0; i < 1000; i++) {
                System.out.println("你是谁？");
            }
        }
    };
}

class Aoo{
    private int id;
    private int aoo;
}


class Boo{

}






