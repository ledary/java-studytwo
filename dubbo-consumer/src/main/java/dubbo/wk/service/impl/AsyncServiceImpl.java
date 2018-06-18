package dubbo.wk.service.impl;

import dubbo.wk.model.OrderEmailModel;
import dubbo.wk.service.AsyncService;
import dubbo.wk.utils.EmailModel;
import dubbo.wk.utils.EmailUtils;
import dubbo.wk.utils.VelocityUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.Future;

/**
 * @author WGP
 * @description
 * @date 2018/6/15
 **/
@Service
//@EnableAsync
public class AsyncServiceImpl implements AsyncService {

    /*
        带有返回值类型的异步方法
     */
    @Override
    @Async
    public Future<Boolean> asyncSendEmail() {
        try{
            /*****************发送邮件***********************/
            EmailUtils emailUtils = new EmailUtils();
            EmailModel model = new EmailModel();
            //发件人地址
            model.setFromEmail("15732622312@163.com");
//            收件人地址
            model.setToEmail("15732622312@163.com");
            //邮件标题
            model.setSubject("测试邮件");


            OrderEmailModel orderEmailModel = new OrderEmailModel();
            orderEmailModel.setTpl("123456789");
            orderEmailModel.setProductInfo("独一无二的商品");
            orderEmailModel.setPrice("99999");
            orderEmailModel.setName("xxx");
            orderEmailModel.setAddress("普天大厦");

            String content = new VelocityUtils().velocityContent(orderEmailModel,"orderemail.vm");
            model.setText(content);
            emailUtils.sendEmail(model);
            System.out.println("异步方法里的方法执行");
            /**************************************************/
        }catch (Exception e){
            e.printStackTrace();
        }
        return new AsyncResult<Boolean>(true);

    }

    /*
    无返回值的异步方法
     */
    @Override
    @Async
    public void voidSendEmail() {
        try{
            EmailUtils emailUtils = new EmailUtils();
            EmailModel model = new EmailModel();
            //发件人地址
            model.setFromEmail("15732622312@163.com");
//            收件人地址
            model.setToEmail("15732622312@163.com");
            //邮件标题
            model.setSubject("测试邮件");


            OrderEmailModel orderEmailModel = new OrderEmailModel();
            orderEmailModel.setOrderNumber("987654321");
            orderEmailModel.setTpl("123456789");
            orderEmailModel.setProductInfo("独一无二的商品");
            orderEmailModel.setPrice("99999");
            orderEmailModel.setName("xxx");
            orderEmailModel.setAddress("普天大厦");

            String content = new VelocityUtils().velocityContent(orderEmailModel,"orderemail.vm");
            model.setText(content);
            emailUtils.sendHtmlEmail(model);
            System.out.println("异步方法里的方法执行");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
