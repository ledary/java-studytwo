package dubbo.wk.model;

import java.io.Serializable;

/**
 * @author WGP
 * @description 发送邮件实体类
 * @date 2018/6/14
 **/
public class EmailModel extends BaseModel  {
    //发件人
    String fromEmail;
    //收件人
    String toEmail;
    //邮件服务器地址
    String emailHost;
    //邮件的主题
    String subject;
    //邮件的消息
    String text;
    String fileName;
    String imgPath;
    String attFile;

    public String getAttFile() {
        return attFile;
    }

    public void setAttFile(String attFile) {
        this.attFile = attFile;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    String filePath;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getEmailHost() {
        return emailHost;
    }

    public void setEmailHost(String emailHost) {
        this.emailHost = emailHost;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
