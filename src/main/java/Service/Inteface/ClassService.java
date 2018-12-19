package Service.Inteface;

import Entity.Class;
import Entity.Student;
import net.sf.json.JSONArray;

public interface ClassService {
    public String addClass(Class aClass);
    public String deleteClass(Class aClass);
    public JSONArray searchClasses(Class aClass);
    public String updateClass(Class aClass, String oldClassID);
    public void excuteDB();
}
