package dubbo.wk;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author WGP
 * @descriptionparent
 * @date 2018/4/29
 **/
public class provider {
    public static void main(String[] args)throws Exception {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
        context.start();
        System.out.println("提供者启动成功！");
        System.in.read();
    }
}
