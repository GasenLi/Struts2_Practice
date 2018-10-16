package Entity;

import java.io.File;

public class UploadFile {
    private static final long serialVersionUID = 1L;

    private File uploadFile;
    private String uploadFileFileName;
    private String uploadFileContentType;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public File getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(File uploadFile) {
        this.uploadFile = uploadFile;
    }

    public String getUploadFileFileName() {
        return uploadFileFileName;
    }

    public void setUploadFileFileName(String uploadFileFileName) {
        this.uploadFileFileName = uploadFileFileName;
    }

    public String getUploadFileContentType() {
        return uploadFileContentType;
    }

    public void setUploadFileContentType(String uploadFileContentType) {
        this.uploadFileContentType = uploadFileContentType;
    }
}
