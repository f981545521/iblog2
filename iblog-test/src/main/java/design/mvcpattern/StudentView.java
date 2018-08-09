package design.mvcpattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 09:20]
 **/
public class StudentView {
    public void printStudentDetails(String studentName, String studentRollNo){
        System.out.println("Student: ");
        System.out.println("Name: " + studentName);
        System.out.println("Roll No: " + studentRollNo);
    }
}
