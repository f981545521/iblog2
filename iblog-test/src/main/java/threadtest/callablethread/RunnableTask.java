package threadtest.callablethread;

import design.mvcpattern.Student;

/**
 * @author youfang
 * @version [1.0.0, 2019-01-25 下午 03:06]
 **/
public class RunnableTask implements Runnable {
    private int id;

    RunnableTask(int id){
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("——————开始执行——————" + id);
        Student student = new Student();
        student.setName("小王");
        student.setRollNo("No.1");
        for (int i = 0; i < 999; i++) {
            student.setRollNo("No." + i);
        }
        System.out.println("—————执行结束——————>" + student);
    }
}
