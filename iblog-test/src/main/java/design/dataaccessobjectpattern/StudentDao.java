package design.dataaccessobjectpattern;

import java.util.List;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 11:56]
 **/
public interface StudentDao {
    public List<Student> getAllStudents();
    public Student getStudent(int rollNo);
    public void updateStudent(Student student);
    public void deleteStudent(Student student);
}
