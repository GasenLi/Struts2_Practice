package Action;

import Entity.UploadFile;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class FileUpload extends ActionSupport implements ModelDriven<UploadFile>{
    private UploadFile uploadFile = new UploadFile();

    @Override
    public UploadFile getModel() {
        return uploadFile;
    }

    @Override
    public String execute() throws Exception {



        return SUCCESS;
    }
}
