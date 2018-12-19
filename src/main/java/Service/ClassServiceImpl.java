package Service;

import Dao.BaseDao;
import Entity.Class;
import Entity.Student;
import Service.Inteface.ClassService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassServiceImpl implements ClassService {
    @Autowired
    private BaseDao baseDao;

    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public String addClass(Class aClass) {
        baseDao.init();

        return  baseDao.insertObj(aClass).toString();
    }

    @Override
    public String deleteClass(Class aClass) {
        baseDao.init();

        if(baseDao.session.get(Class.class, aClass.getClassID()).getClassStuNum()>0){
            return "请先移除完这个班级所有的学生！";
        }
        return  baseDao.delete(aClass, aClass.getClassID()).toString();
    }

    @Override
    public JSONArray searchClasses(Class aClass) {
        baseDao.init();

        List<Object> searchResults = search();
        return convertListToJson(searchResults, aClass);

    }

    @Override
    public String updateClass(Class aClass, String oldClassID) {
        baseDao.init();

        if(oldClassID.equals(aClass.getClassID())){
            return  "true";
        }else if(baseDao.session.get(Class.class, aClass.getClassID()) == null){
            Class oldClass = baseDao.session.get(Class.class, oldClassID);

            aClass.setClassStuNum(oldClass.getClassStuNum());
            String optionResult1 = baseDao.insertObj(aClass).toString();
            for(Student student : oldClass.getStudents()){
                student.setClassID(aClass.getClassID());
                baseDao.updateObj(student);
            }

            String optionResult2 = baseDao.delete(aClass, oldClassID).toString();

            return  (optionResult1.equals(optionResult2) & optionResult1.equals("true")) ? "true" : "false";
        }else {
            return  "该班级ID已经使用过了";
        }
    }

    @Override
    public void excuteDB() {
        baseDao.over();
    }

    public List<Object> searchStuByClassID(String classID) {
        String hql = "from Student ";
        hql += "where ClassID = '" + classID +"'";

        return baseDao.custom(hql);
    }

    public List<Object> search() {
        String hql = "from Class ";

        return baseDao.custom(hql);
    }

    public JSONArray convertListToJson(List<Object> lists, Class aClass) {
        JSONArray result = new JSONArray();
        JSONObject jsonObj;

        for (int i = 0; i < lists.size(); i++) {
            jsonObj = new JSONObject();
            aClass = (Class) lists.get(i);

            jsonObj.put("classID", aClass.getClassID());
            jsonObj.put("classStuNum", aClass.getClassStuNum());

            result.add(jsonObj);
        }

        return result;
    }
}
