package Action;

import Dao.BaseDao;
import Entity.Class;
import Entity.Student;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

public class StudentManage extends ActionSupport implements ModelDriven<Student> {
    public BaseDao baseDao = new BaseDao();
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

        //System.out.println("ID  " + student.getStudentID() + " ====");
        String optionResult = null;

        switch (option) {
            case "insert":
                Class c1 = baseDao.session.get(Class.class, student.getClassID());
                Student s2 = baseDao.session.get(Student.class, student.getStudentID());
                if(c1 == null){
                    optionResult = "请先添加对应的班级！";
                }else if(s2 != null){
                    optionResult = "该学生ID应经被使用了！";
                }else {
                    optionResult = baseDao.insertObj(student);
                    addClassStuNum(student.getClassID());
                }
                break;
            case "delete":
                Student s1 = baseDao.session.get(Student.class, student.getStudentID());
                optionResult = baseDao.delete(student, s1.getStudentID());
                reduceClassStuNum(s1.getClassID());
                break;
            case "search":
                List<Object> searchResults = search();
                convertListToJson(searchResults);
                break;
            case "update":
                Boolean isUpdateStuID = !oldStudentID.equals(student.getStudentID());
                Boolean isUpdateClassID = !oldClassID.equals(student.getClassID());

                if (isUpdateClassID & isUpdateStuID) {
                    if (baseDao.session.get(Class.class, student.getClassID()) == null) {
                        optionResult = "请先添加对应的班级！";
                    }else if(baseDao.session.get(Student.class, student.getStudentID()) != null){
                        optionResult = "修改的ID应经被使用了！";
                    }else {
                        String optionResult1 = baseDao.insertObj(student);
                        String optionResult2 = baseDao.delete(student, oldStudentID);

                        optionResult = (optionResult1.equals(optionResult2) & optionResult1.equals("true")) ? "true" : "false";

                        addClassStuNum(student.getClassID());
                        reduceClassStuNum(oldClassID);
                    }


                } else if (isUpdateClassID == false & isUpdateStuID == false) {
                    optionResult = baseDao.updateObj(student);
                    break;

                } else if (isUpdateClassID == true & isUpdateStuID == false) {
                    if (baseDao.session.get(Class.class, student.getClassID()) != null) {
                        optionResult = baseDao.updateObj(student);
                        addClassStuNum(student.getClassID());
                        reduceClassStuNum(oldClassID);
                        break;
                    } else {
                        optionResult = "请先添加对应的班级！";
                    }

                } else if (isUpdateClassID == false & isUpdateStuID == true) {
                    if (baseDao.session.get(Student.class, student.getStudentID()) == null) {
                        optionResult = updateStuClassID(student, oldStudentID);
                    } else {
                        optionResult = "修改的ID应经被使用了！";
                    }
                }
                break;
        }

        baseDao.over();

        if (!option.equals("search")) {
            jsonObj = new JSONObject();
            jsonObj.put("optionResult", optionResult);

            result.add(jsonObj);
        }

        return SUCCESS;
    }

    public String updateStuClassID(Student student, String oldStudentID){
        String optionResult1 = baseDao.insertObj(student);
        String optionResult2 = baseDao.delete(student, oldStudentID);

        String optionResult = (optionResult1.equals(optionResult2) & optionResult1.equals("true")) ? "true" : "false";
        return optionResult;
    }

    public void addClassStuNum(String classID) {
        Class c1 = baseDao.session.get(Class.class, classID);
        c1.setClassStuNum(c1.getClassStuNum() + 1);
        baseDao.updateObj(c1);
    }

    public void reduceClassStuNum(String classID) {
        Class c1 = baseDao.session.get(Class.class, classID);
        c1.setClassStuNum(c1.getClassStuNum() - 1);
        baseDao.updateObj(c1);
    }

    public void convertListToJson(List<Object> lists) {
        for (int i = 0; i < lists.size(); i++) {
            jsonObj = new JSONObject();
            student = (Student) lists.get(i);

            jsonObj.put("studentID", student.getStudentID());
            jsonObj.put("studentName", student.getStudentName());
            jsonObj.put("studentAge", student.getStudentAge());
            jsonObj.put("classID", student.getClassID());

            result.add(jsonObj);
        }
    }

    public void convertMenu() {
        switch (menu) {
            case "name":
                menu = "StudentName";
                break;
            case "age":
                menu = "StudentAge";
                break;
            case "sno":
                menu = "StudentID";
                break;
        }
    }

    public void convertRange() {
        switch (range) {
            case "greater":
                range = ">";
                break;
            case "gore":
                range = ">=";
                break;
            case "equal":
                range = "=";
                break;
            case "sore":
                range = "<=";
                break;
            case "smaller":
                range = "<";
                break;

            case "include":
                range = "like";
                break;
        }
    }

    public List<Object> search() {
        convertMenu();
        convertRange();

        String hql = "from Student ";
        hql += "where ClassID = " + student.getClassID();

        if (!range.equals("like")) {
            hql += " and " + menu + " " + range + " '" + value + "'";
        } else {
            hql += " and " + menu + " " + range + " '%" + value + "%'";
        }

        return baseDao.custom(hql);
    }
}
