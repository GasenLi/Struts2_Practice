package Action;

import Entity.Student;
import Service.Inteface.StudentService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
@Scope("prototype")
public class StudentManage extends ActionSupport implements ModelDriven<Student> {
    @Autowired
    private StudentService studentService;
    private JSONObject jsonObj;

    private Student student = new Student();
    private String option;

    private String menu;
    private String range;
    private String value;
    private String classID;
    private String oldStudentID;
    private String oldClassID;

    private JSONArray result = new JSONArray();

    public JSONArray getResult() {
        return result;
    }

    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    public void setResult(JSONArray result) {
        this.result = result;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getOldStudentID() {
        return oldStudentID;
    }

    public void setOldStudentID(String oldStudentID) {
        this.oldStudentID = oldStudentID;
    }

    public String getOldClassID() {
        return oldClassID;
    }

    public void setOldClassID(String oldClassID) {
        this.oldClassID = oldClassID;
    }

    @Override
    public Student getModel() {
        return student;
    }

    @Override
    public String execute() throws Exception {
        String optionResult = null;

        switch (option) {
            case "insert":
                optionResult =studentService.addStudent(student);
                break;
            case "delete":
                optionResult =studentService.deleteStudent(student);
                break;
            case "search":
                result = studentService.searchStudents(student, range, menu, value);
                break;
            case "update":
                optionResult = studentService.updateStudent(student, oldStudentID, oldClassID);
                break;
        }

        //提交事务并关闭baseDao
        studentService.excuteDB();

        //设置返回值
        if (!option.equals("search")) {
            jsonObj = new JSONObject();
            jsonObj.put("optionResult", optionResult);
            result.add(jsonObj);
        }

        return SUCCESS;
    }
}
