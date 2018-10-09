package Intercepter;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;

import static com.opensymphony.xwork2.Action.ERROR;

public class LoginIntercepter extends AbstractInterceptor{

    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        ActionContext actionContext = actionInvocation.getInvocationContext();
        Object user = actionContext.getSession().get("user");

        if(user != null){

            return actionInvocation.invoke();
        }else {
            //            actionContext.put("info","notLoggedIn");

            JSONObject result = new JSONObject();
            result.put("info","notLoggedIn");

            //返回结果
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setContentType("text/html;charset=UTF-8");
            //设置不使用缓存
            response.setHeader("Cache-Control","no-cache");
            response.getWriter().write(result+"");
            response.getWriter().flush();
            response.getWriter().close();

            return ERROR;
        }
    }
}
