package Action;

import Entity.UploadFile;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import net.sf.json.JSONObject;

import java.io.*;

public class FileUpload extends ActionSupport implements ModelDriven<UploadFile>{
    private UploadFile uploadFile = new UploadFile();

    private JSONObject result = new JSONObject();

    public JSONObject getResult() {
        return result;
    }

    public void setResult(JSONObject result) {
        this.result = result;
    }

    @Override
    public UploadFile getModel() {
        return uploadFile;
    }

    @Override
    public String execute() throws Exception {
        //输入
        InputStream inputStream = new FileInputStream(uploadFile.getUploadFile());

        //输出
        String uploadPath = ("E:\\workplace\\Java EE\\实验一\\上传文件");
        File toFile = new File(uploadPath, uploadFile.getUploadFileFileName());
        OutputStream outputStream = new FileOutputStream(toFile);

        //传输
        byte[] buffer = new byte[1024];
        int length = 0;
        while (-1!=(length = inputStream.read(buffer,0,buffer.length))){
            outputStream.write(buffer);
        }

        //关闭
        inputStream.close();
        outputStream.close();


        //返回结果
        result.put("info", "uploadSuccess");

        return SUCCESS;
    }
}
