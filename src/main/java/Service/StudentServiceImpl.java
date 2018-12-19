package Service;

import Dao.BaseDao;
import Entity.Class;
import Entity.Student;
import Service.Inteface.StudentService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{
    @Autowired
    private BaseDao baseDao;

    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public String addStudent(Student student) {
        baseDao.init();

        Class c1 = baseDao.session.get(Class.class, student.getClassID());
        Student s2 = baseDao.session.get(Student.class, student.getStudentID());
        if(c1 == null){
            return "请先添加对应的班级！";
        }else if(s2 != null){
            return "该学生ID应经被使用了！";
        }else {
            addClassStuNum(student.getClassID());
            return baseDao.insertObj(student).toString();
        }
    }

    @Override
    public String deleteStudent(Student student) {
        baseDao.init();

        Student s1 = baseDao.session.get(Student.class, student.getStudentID());
        reduceClassStuNum(s1.getClassID());
        return baseDao.delete(student, s1.getStudentID()).toString();
    }

    @Override
    public JSONArray searchStudents(Student student, String range, String menu, String value) {
        baseDao.init();

        List<Object> searchResults = search(student, range, menu, value);
        return convertListToJson(searchResults, student);
    }

    @Override
    public String updateStudent(Student student, String oldStudentID, String oldClassID) {
        baseDao.init();

        Boolean isUpdateStuID = !oldStudentID.equals(student.getStudentID());
        Boolean isUpdateClassID = !oldClassID.equals(student.getClassID());

        if (isUpdateClassID & isUpdateStuID) {
            if (baseDao.session.get(Class.class, student.getClassID()) == null) {
                return "请先添加对应的班级！";
            }else if(baseDao.session.get(Student.class, student.getStudentID()) != null){
                return "修改的ID应经被使用了！";
            }else {
                String optionResult1 = baseDao.insertObj(student).toString();
                String optionResult2 = baseDao.delete(student, oldStudentID).toString();

                addClassStuNum(student.getClassID());
                reduceClassStuNum(oldClassID);
                return (optionResult1.equals(optionResult2) & optionResult1.equals("true")) ? "true" : "false";
            }


        } else if (isUpdateClassID == false & isUpdateStuID == false) {
            return baseDao.updateObj(student).toString();

        } else if (isUpdateClassID == true & isUpdateStuID == false) {
            if (baseDao.session.get(Class.class, student.getClassID()) != null) {
                addClassStuNum(student.getClassID());
                reduceClassStuNum(oldClassID);
                return baseDao.updateObj(student).toString();
            } else {
                return "请先添加对应的班级！";
            }

        } else if (isUpdateClassID == false & isUpdateStuID == true) {
            if (baseDao.session.get(Student.class, student.getStudentID()) == null) {
                return updateStuClassID(student, oldStudentID);
            } else {
                return "修改的ID应经被使用了！";
            }
        }
        return null;
    }

    @Override
    public void excuteDB(){
        baseDao.over();
    }

    public String updateStuClassID(Student student, String oldStudentID){
        String optionResult1 = baseDao.insertObj(student).toString();
        String optionResult2 = baseDao.delete(student, oldStudentID).toString();

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

    public JSONArray convertListToJson(List<Object> lists , Student student) {
        JSONArray result = new JSONArray();
        JSONObject jsonObj;

        for (int i = 0; i < lists.size(); i++) {
            jsonObj = new JSONObject();
            student = (Student) lists.get(i);

            jsonObj.put("studentID", student.getStudentID());
            jsonObj.put("studentName", student.getStudentName());
            jsonObj.put("studentAge", student.getStudentAge());
            jsonObj.put("classID", student.getClassID());

            result.add(jsonObj);
        }

        return result;
    }

    public String convertMenu(String menu) {
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

        return menu;
    }

    public String convertRange(String range) {
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

        return range;
    }

    public List<Object> search(Student student, String range, String menu, String value) {
        menu = convertMenu(menu);
        range = convertRange(range);

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
