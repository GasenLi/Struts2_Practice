import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.json.annotations.JSON;

import java.util.ArrayList;

public class StudentQuery extends ActionSupport {
    private static final String[][] data = new String[][]{
            {"0001","张一","22"},
            {"0002","张二","20"},
            {"0003","张三","17"},
            {"0004","张无忌","20"},
            {"0005","李一","19"},
            {"0006","李二","19"},
            {"0007","李三","22"},
            {"0008","王一","22"},
            {"0009","王二","22"}
    };

    private String menu;
    private String range;
    private String value;
    private JSONArray result = new JSONArray();

    public static String[][] getData() {
        return data;
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

    public JSONArray getResult() {
        return result;
    }

    public void setResult(JSONArray result) {
        this.result = result;
    }

    @Override
    public String execute() throws Exception {

        if (menu.equals("age")) {
            if (range.equals("greater")) {
                for (int i = 0; i < data.length; i++) {
                    if (Integer.parseInt(data[i][2]) > Integer.parseInt(value)) {
                        result.add(data[i]);
                    }
                }
            } else if (range.equals("gore")) {
                for (int i = 0; i < data.length; i++) {
                    if (Integer.parseInt(data[i][2]) >= Integer.parseInt(value)) {
                        result.add(data[i]);
                    }
                }
            } else if (range.equals("equal")) {
                for (int i = 0; i < data.length; i++) {
                    if (Integer.parseInt(data[i][2]) == Integer.parseInt(value)) {
                        result.add(data[i]);
                    }
                }
            } else if (range.equals("sore")) {
                for (int i = 0; i < data.length; i++) {
                    if (Integer.parseInt(data[i][2]) <= Integer.parseInt(value)) {
                        result.add(data[i]);
                    }
                }
            } else if (range.equals("smaller")) {
                for (int i = 0; i < data.length; i++) {
                    if (Integer.parseInt(data[i][2]) < Integer.parseInt(value)) {
                        result.add(data[i]);
                    }
                }
            }
        } else {
            int order = 0;
            if (menu.equals("name")) {
                order = 1;
            }

            if (range.equals("include")) {
                for (int i = 0; i < data.length; i++) {
                    if (data[i][order].contains(value)) {
                        result.add(data[i]);
                    }
                }
            } else if (range.equals("equal")) {
                for (int i = 0; i < data.length; i++) {
                    if (data[i][order].equals(value)) {
                        result.add(data[i]);
                    }
                }
            }
        }
        
        return "success";
    }
}
