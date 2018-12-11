package Action;

import Dao.BaseDao;
import Entity.Class;
import Entity.Student;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

public class ClassManage extends ActionSupport implements ModelDriven<Class> {
    private Class aClass = new Class();
    private JSONArray result = new JSONArray();
    private BaseDao baseDao = new BaseDao();

    private JSONObject jsonObj;
    private String option;

    @Override
    public Class getModel() {
        return aClass;
    }

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

    @Override
    public String execute() throws Exception {
        String optionResult = null;

        switch (option){
            case "insert": optionResult = baseDao.insertObj(aClass);break;
            case "delete": optionResult = baseDao.delete(aClass, aClass.getClassID());break;
            case "search": List<Object> searchResults = search();
                            convertListToJson(searchResults);break;
            case "update": optionResult = baseDao.updateObj(aClass);break;
        }

        try{
            baseDao.over();
        }catch (Exception ex){
            System.out.println("---异常类名--- ： " + ex.getClass().getName());
            switch (option){
                case "delete": if(ex.getClass().getName().equals("javax.persistence.PersistenceException")){
                                     optionResult = "请先移除完这个班级所有的学生！";
                                };
            }
        }

        if(!option.equals("search")){
            jsonObj = new JSONObject();
            jsonObj.put("optionResult", optionResult);

            result.add(jsonObj);
        }

        return SUCCESS;
    }

    public List<Object> search(){
        String hql = "from Class ";
        //hql += "where ClassID = *";

        return baseDao.custom(hql);
    }

    public void convertListToJson(List<Object> lists){
        for(int i=0; i<lists.size(); i++){
            jsonObj = new JSONObject();
            aClass = (Class) lists.get(i);

            jsonObj.put("classID", aClass.getClassID());
            jsonObj.put("classStuNum", aClass.getClassStuNum());

            result.add(jsonObj);
        }
    }
}
