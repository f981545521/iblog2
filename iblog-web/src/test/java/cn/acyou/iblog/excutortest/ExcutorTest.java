package cn.acyou.iblog.excutortest;

import cn.acyou.iblog.entity.People;
import cn.acyou.iblog.entity.Student;
import cn.acyou.iblog.entity.Teacher;
import cn.acyou.iblog.executor.AfterCommitExecutor;
import cn.acyou.iblog.maintest.BaseTest;
import cn.acyou.iblog.orika.OrikaMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.junit.Test;

/**
 * @author youfang
 * @date 2018-02-09 12:48
 **/
public class ExcutorTest extends BaseTest {

    @Test
    public void test1(){
        AfterCommitExecutor executor = ctx.getBean("afterCommitExecutor", AfterCommitExecutor.class);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 100; i > 0; i--) {
                    if (i == 50){
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(i);
                }
            }
        });
        log.info("执行结束！！");
        System.out.println(executor);
    }


    @Test
    public void testOrikaMapper(){
        OrikaMapper orikaMapper = ctx.getBean("orikaMapper", OrikaMapper.class);
        People people = new People(1,"张飞",23);
        Teacher teacher = orikaMapper.convert(people,Teacher.class);
        System.out.println(teacher.getAddress() == null);
        System.out.println(people);
        System.out.println(teacher);
        Student student = orikaMapper.convert(people,Student.class);
        System.out.println(student);
    }

    @Test
    public void testOrikaMapper2(){
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(People.class, Student.class).field("name","userName").byDefault().register();
        People people = new People(1,"张飞",23);
        Student student = mapperFactory.getMapperFacade().map(people, Student.class);
        System.out.println(student);

    }
}
