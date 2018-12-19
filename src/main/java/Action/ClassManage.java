package Action;

import Dao.BaseDao;
import Entity.Class;
import Entity.Student;
import Service.Inteface.ClassService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@Scope("prototype")
public class ClassManage extends ActionSupport implements ModelDriven<Class> {
    @Autowired
    private ClassService classService;

    private Class aClass = new Class();
    private JSONArray result = new JSONArray();

    private JSONObject jsonObj;
    private String option;
    private String oldClassID;

    @Override
    public Class getModel() {
        return aClass;
    }

    public void setClassManage(ClassService classService) {
        this.classService = classService;
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

    public String getOldClassID() {
        return oldClassID;
    }

    public void setOldClassID(String oldClassID) {
        this.oldClassID = oldClassID;
    }

    @Override
    public String execute() throws Exception {
        String optionResult = null;

        switch (option) {
            case "insert":
                optionResult = classService.addClass(aClass);
                break;
            case "delete":
                optionResult = classService.deleteClass(aClass);
                break;
            case "search":
                result = classService.searchClasses(aClass);
                break;
            case "update":
                optionResult = classService.updateClass(aClass, oldClassID);
                break;
        }

        //提交事务并关闭baseDao
        classService.excuteDB();


        //设置返回值
        if (!option.equals("search")) {
            jsonObj = new JSONObject();
            jsonObj.put("optionResult", optionResult);
            result.add(jsonObj);
        }

        return SUCCESS;
    }
}
