package Action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class FileDownload extends ActionSupport{
    private String filename;
    private String contentType;

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilename() throws UnsupportedEncodingException {
        return URLEncoder.encode(filename,"UTF-8");
    }

    public String getContentType(){
        return ServletActionContext.getServletContext().getMimeType(filename);
    }

    public InputStream getDownloadFile() throws FileNotFoundException {
        return new FileInputStream("E:\\workplace\\Java EE\\实验一\\下载文件\\" + filename);
    }


    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }
}
