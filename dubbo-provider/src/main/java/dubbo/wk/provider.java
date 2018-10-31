package dubbo.wk;

import com.alibaba.fastjson.JSON;
import dubbo.wk.model.domain.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author WGP
 * @descriptionparent
 * @date 2018/4/29
 **/
public class provider {
    public static void main(String[] args)throws Exception {
         final Logger logger = LoggerFactory.getLogger(provider.class);
//        ClassPathXmlApplicationContext context =
//                new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
//        context.start();
//        System.out.println("提供者启动成功！");
//        System.in.read();

            List<UserEntity> list = new ArrayList<>();
        Map<Long,UserEntity> map = list.stream().collect(Collectors.toMap(UserEntity::getId, Function.identity()));
        System.out.println(map.size());


    }
    public void test(BigDecimal c,BigDecimal a){
        System.out.println(c.add(a));

    }
}
