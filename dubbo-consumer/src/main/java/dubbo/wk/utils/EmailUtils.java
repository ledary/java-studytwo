package dubbo.wk.utils;

import dubbo.wk.model.EmailModel;
import dubbo.wk.model.OrderEmailModel;
import org.junit.Test;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * @author WGP
 * @description发送邮件工具类
 * @date 2018/6/14
 **/
public class EmailUtils {
    private static Properties props;

    static {
        props = new Properties();
        //配置简单邮件传输协议
        props.setProperty("mail.transport.protocol", "smtp");
        //配置邮件发送服务器
        props.put("mail.smtp.host", "smtp.163.com");
//        props.put("mail.smtp.host","localhost");
        //配置邮件发送服务器端口
        props.put("mail.smtp.port", "25");
        //验证为true
        props.put("mail.smtp.auth", true);
    }

    /*
    简单邮件发送
     */
    public void sendEmail(EmailModel emailModel) {
        //建立会话 并赋予权限信息
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("15732622312@163.com", "wywgphcy155300");
            }
        });
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(emailModel.getFromEmail(), "发件人"));
            message.setSubject(emailModel.getSubject());
            message.setText(emailModel.getText());
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailModel.getToEmail(), "收件人"));

            Transport.send(message);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void sendHtmlEmail(EmailModel model) {
        //建立会话 并赋予权限信息
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("15732622312@163.com", "wywgphcy155300");
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(model.getFromEmail(), "发件人"));
            message.setSubject(model.getSubject());
            //发件人不用encode编码，附件的名字需要encode编码
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(model.getToEmail(), "收件人"));
            //整个邮件的MIME消息体
            MimeMultipart msgMultipart = new MimeMultipart("mixed");
            //邮件正文的消息体
            MimeMultipart htmlMultipart = new MimeMultipart("related");
            //邮件正文body块
            BodyPart contentPart = new MimeBodyPart();
            BodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(model.getText(), "text/html;charset=gbk");
            htmlMultipart.addBodyPart(htmlPart);
            contentPart.setContent(htmlMultipart);
            msgMultipart.addBodyPart(contentPart);

            message.setContent(msgMultipart);
            message.saveChanges();
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendImg(EmailModel model) {
        //建立会话 并赋予权限信息
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("15732622312@163.com", "wywgphcy155300");
            }
        });

        try {
            //整个邮件  设置会话基本信息
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(model.getFromEmail(), "发件人"));
            message.setSubject(model.getSubject());
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(model.getToEmail(), "收件人"));

            //整个邮件的MIME消息体
            MimeMultipart msgMultipart = new MimeMultipart("mixed");


            //附件信息
            BodyPart att1 = new MimeBodyPart();
            DataSource att1Source = new FileDataSource(new File(model.getAttFile()));
            DataHandler att1Handler = new DataHandler(att1Source);
            att1.setDataHandler(att1Handler);
            att1.setFileName(MimeUtility.encodeText("附件1.txt"));
            msgMultipart.addBodyPart(att1);

            //邮件正文
            BodyPart contentPart = new MimeBodyPart();

            // 邮件正文内容  包括 正文信息 和图片信息
            MimeMultipart related = new MimeMultipart("related");
            BodyPart htmlPart = new MimeBodyPart();
            MimeBodyPart imgPart = new MimeBodyPart();

            imgPart.setContentID("a");

            DataSource source = new FileDataSource(new File(model.getImgPath()));
            DataHandler handler = new DataHandler(source);
            imgPart.setDataHandler(handler);
            htmlPart.setContent(model.getText() + "<img src='cid:a'>", "text/html;charset=gbk");

            //把邮件正文和图片信息放入正文内
            related.addBodyPart(htmlPart);
            related.addBodyPart(imgPart);
            contentPart.setContent(related);

            //把正文内容放入邮件消息体
            msgMultipart.addBodyPart(contentPart);
            //消息体和邮件关联
            message.setContent(msgMultipart);

            //保存邮件内容
            message.saveChanges();
            //发送邮件
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        EmailUtils emailUtils = new EmailUtils();
        EmailModel model = new EmailModel();

        model.setFromEmail("15732622312@163.com");
        model.setToEmail("15732622312@163.com");
        model.setSubject("测试邮件");
        model.setText("text 消息体");
        emailUtils.sendEmail(model);
    }


    @Test
    public void test2() {
        EmailUtils emailUtils = new EmailUtils();
        EmailModel model = new EmailModel();
        model.setFilePath("F:" + File.separator + "个人文档" + File.separator + "wgp_NuGetAPIKey.txt");
        model.setFileName("file.txt");
        model.setImgPath("C:\\Users\\WGP\\Pictures\\妈妈的照片\\00.jpg");
        model.setFromEmail("15732622312@163.com");
        model.setToEmail("15732622312@163.com");
        model.setSubject("测试邮件");
        model.setText("text 消息体");
        model.setAttFile("F:\\logs\\examination-service.log");
        emailUtils.sendImg(model);
    }

    @Test
    public void test3() {
        EmailUtils emailUtils = new EmailUtils();

        OrderEmailModel orderEmailModel = new OrderEmailModel();
        orderEmailModel.setOrderNumber("987654321");
        orderEmailModel.setTpl("123456789");
        orderEmailModel.setProductInfo("独一无二的商品");
        orderEmailModel.setPrice("99999");
        orderEmailModel.setName("xxx");
        orderEmailModel.setAddress("普天大厦");


        EmailModel model = new EmailModel();
        model.setFilePath("F:" + File.separator + "个人文档" + File.separator + "wgp_NuGetAPIKey.txt");
        model.setFileName("file.txt");
        model.setImgPath("C:\\Users\\WGP\\Pictures\\妈妈的照片\\00.jpg");
        model.setFromEmail("15732622312@163.com");
        model.setToEmail("15732622312@163.com");
        model.setSubject("测试邮件");
        model.setText(new VelocityUtils().velocityContent(orderEmailModel,"orderemail.vm"));
        model.setAttFile("F:\\logs\\examination-service.log");
        String content = new VelocityUtils().velocityContent(orderEmailModel, "orderemail.vm");
        model.setText(content);
        emailUtils.sendImg(model);
        System.out.println("异步方法里的方法执行");

    }

}
