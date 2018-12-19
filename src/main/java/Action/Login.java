package Action;

import Entity.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import net.sf.json.JSONObject;

public class Login extends ActionSupport implements ModelDriven<User>{
    private User user = new User();

    public JSONObject getResult() {
        return result;
    }

    public void setResult(JSONObject result) {
        this.result = result;
    }

    private JSONObject result = new JSONObject();

    @Override
    public User getModel() {
        return user;
    }

    @Override
    public String execute() throws Exception {

        System.out.println(user.getUsername()+ " " + user.getPassword() + " ====");


        String[][] data = User.getData();
         for(int i=0;i<data.length;i++){
            if(data[i][1].equals(user.getUsername())){
                if(data[i][2].equals(user.getPassword())){
                    ActionContext actionContext = ActionContext.getContext();
                    actionContext.getSession().put("user",user);

                    result.put("info","loginSuccess");
                    return SUCCESS;
                }

                result.put("info","loginFailed");
                return SUCCESS;
            }
        }

        result.put("info","none");
        return SUCCESS;
    }
}
