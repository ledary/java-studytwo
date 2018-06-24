package dubbo.wk;

import dubbo.wk.model.domain.UserEntity;
import dubbo.wk.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Field;

/**
 * @author WGP
 * @descriptionparent
 * @date 2018/4/30
 **/
public class Mybatis {
    public static void main(String[] args) {
        String path = "classpath:spring/spring-context.xml";
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext(path);
        UserService userService = context.getBean(UserService.class);
        UserEntity userEntity = userService.findUserByPrimaryKey(1L);
        Mybatis mybatis  = new Mybatis();
        mybatis.printEntity(userEntity);


    }


    public <T> void printEntity(T t) {

        Class clazz = t.getClass();
        Field[]  fields = clazz.getDeclaredFields();
        try{
            for (Field field : fields) {
                field.setAccessible(true);
                System.out.println(field.getName() + ":" + field.get(t));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
