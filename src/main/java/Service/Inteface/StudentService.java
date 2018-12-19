package Service.Inteface;

import Entity.Student;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface StudentService {
    public String addStudent(Student student);
    public String deleteStudent(Student student);
    public JSONArray searchStudents(Student student, String range, String menu, String value);
    public String updateStudent(Student student, String oldStudentID, String oldClassID);
    public void excuteDB();
}
