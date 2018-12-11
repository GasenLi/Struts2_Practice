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
    private BaseDao baseDao = new BaseDao();
    private JSONObject jsonObj;

    private Student student = new Student();
    private String option;

    private String menu;
    private String range;
    private String value;
    private String classID;

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

    @Override
    public Student getModel() {
        return student;
    }

    @Override
    public String execute() throws Exception {

        //System.out.println("ID  " + student.getStudentID() + " ====");
        String optionResult = null;

        switch (option){
            case "insert": optionResult = baseDao.insertObj(student);
                            Class c1 = baseDao.session.get(Class.class,student.getClassID());
                            c1.setClassStuNum(c1.getClassStuNum()+1);
                            baseDao.updateObj(c1);break;
            case "delete": Student s1 = baseDao.session.get(Student.class,student.getStudentID());
                            Class c2 = baseDao.session.get(Class.class,s1.getClassID());
                            optionResult = baseDao.delete(student, student.getStudentID());
                            c2.setClassStuNum(c2.getClassStuNum()-1);
                            baseDao.updateObj(c2);break;
            case "search": List<Object> searchResults = search();
                           convertListToJson(searchResults);break;
            case "update": optionResult = baseDao.updateObj(student);break;
        }

        if(!option.equals("search")){
            jsonObj = new JSONObject();
            jsonObj.put("optionResult", optionResult);

            result.add(jsonObj);
        }

        baseDao.over();
        return SUCCESS;
    }

    public void convertListToJson(List<Object> lists){
        for(int i=0; i<lists.size(); i++){
            jsonObj = new JSONObject();
            student = (Student) lists.get(i);

            jsonObj.put("studentID", student.getStudentID());
            jsonObj.put("studentName", student.getStudentName());
            jsonObj.put("studentAge", student.getStudentAge());
            jsonObj.put("classID", student.getClassID());

            result.add(jsonObj);
        }
    }

    public void convertMenu(){
        switch (menu){
            case "name": menu = "StudentName";break;
            case "age": menu = "StudentAge";break;
            case "ID": menu = "StudentID";break;
        }
    }

    public void convertRange(){
            switch (range){
                case "greater": range = ">";break;
                case "gore": range = ">=";break;
                case "equal": range = "=";break;
                case "sore": range = "<=";break;
                case "smaller": range = "<";break;

                case "include": range = "like";break;
            }
    }

    public List<Object> search(){
        convertMenu();
        convertRange();

        String hql = "from Student ";
        hql += "where ClassID = " + student.getClassID();

        if(!range.equals("like")){
            hql += " and " + menu + " " + range + " " + value;
        }else {
            hql += " and " + menu + " " + range + " '%" + value + "%'";
        }

        return baseDao.custom(hql);
    }
}
