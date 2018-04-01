package reflecttest;

import com.sun.org.glassfish.gmbal.Description;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author youfang
 * @date 2018-04-01 上午 10:42
 **/
public class ReflectTest {

    public static void main(String[] args) throws Exception {
        System.out.println(Arrays.toString(args));
        System.out.println(args[0]);
        System.out.println(args[1]);
    }

    @Test
    public void test11() throws Exception {
        Class teacher = Class.forName("reflecttest.Teacher");
        Method[] methods = teacher.getDeclaredMethods();
        Object obj = teacher.newInstance();
        for (Method method : methods){
            System.out.println(method);
            System.out.println(method.invoke(obj, "英语"));
        }
    }


    @Test
    public void test12() throws Exception {
        Class clz = Class.forName("reflecttest.RecurrenceFreq");
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields){
            System.out.println(field.getName());
            Annotation[] annotations = field.getDeclaredAnnotations();
            for (Annotation annotation : annotations){
                System.out.println(annotation.annotationType());
                System.out.println(annotation.toString());
            }
        }
    }

    @Test
    public void test13() throws Exception {
        Map<String, String> map = new HashMap<>();
        Class<RecurrenceFreq> clz = RecurrenceFreq.class;
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields){
            if (field.isAnnotationPresent(Description.class)){
                Description description = field.getAnnotation(Description.class);
                map.put(field.getName(), description.value());
            }
        }
        System.out.println(map);
    }

    @Test
    public void test21(){
        RecurrenceFreq[] recurrenceFreqs = RecurrenceFreq.values();
        System.out.println(recurrenceFreqs[0].name());
    }

}
