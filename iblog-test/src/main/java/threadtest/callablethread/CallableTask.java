package threadtest.callablethread;


import design.mvcpattern.Student;

import java.util.concurrent.Callable;

/**
 * @author youfang
 * @version [1.0.0, 2019-01-25 下午 02:52]
 **/
public class CallableTask implements Callable<Student> {

    private int id;

    CallableTask(int id){
        this.id = id;
    }

    /**
     * 任务的具体过程，一旦任务传给ExecutorService的submit方法，则该方法自动在一个线程上执行。
     * @return student
     */
    @Override
    public Student call() {
        System.out.println("——————开始执行——————" + id);
        Student student = new Student();
        student.setName("小王");
        student.setRollNo("No.1");
        for (int i = 0; i < 999; i++) {
            student.setRollNo("No." + i);
        }
        System.out.println("—————执行结束——————>" + student);
        return student;
    }
}
