package dubbo.wk;

import dubbo.wk.controller.DemoController;
import dubbo.wk.model.DemoRemoteModel;
import dubbo.wk.scheduler.TimerTask;
import dubbo.wk.service.CourseService;
import dubbo.wk.service.IDemoService;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * @author WGP
 * @description  dubbo 消费者启动端
 * @date 2018/4/29
 **/
public class consumer {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
        CourseService iDemoService = context.getBean(CourseService.class);
        DemoRemoteModel model = iDemoService.findDemoModel();
        System.out.println(model.getUserName());
    }


    @Test
    public void testTask(){
        BeanFactory factory = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
//        TimerTask ta = factory.get

    }
}
