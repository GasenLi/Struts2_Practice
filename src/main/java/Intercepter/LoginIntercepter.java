package Intercepter;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;

import static com.opensymphony.xwork2.Action.ERROR;

public class LoginIntercepter extends AbstractInterceptor{

    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        ActionContext actionContext = actionInvocation.getInvocationContext();
        Object user = actionContext.getSession().get("user");

        if(user != null){
            return actionInvocation.invoke();
        }else {
            JSONObject result = new JSONObject();
            result.put("info","notLoggedIn");

            //将action的resultObj设值
            Method method = actionInvocation.getProxy().getAction().getClass().getMethod("setResultObj",JSONObject.class);
            method.invoke(actionInvocation.getProxy().getAction(), result);

            return ERROR;
        }
    }
}
