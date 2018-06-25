package dubbo.wk.mybatis;

import dubbo.wk.dao.UserMapper;
import dubbo.wk.model.domain.UserEntity;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

/**
 * 编程式 使用mybatis 测试事物级别
 * Created by wgp on 2018/6/24.
 */
public class XmlUserDao {

    public static void main(String[] args) {
       new XmlUserDao().test();
    }

    @Test
public void test4(){
    String resource = "mybatis-config.xml";
    SqlSessionFactory sqlSessionFactory=null;
    SqlSession session = null;
    try{
        InputStream in = new ClassPathResource(resource).getInputStream();
        //创建单例工厂
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
        session = sqlSessionFactory.openSession();
        XmlUserDao xmlMybatisTest = new XmlUserDao();
        UserMapper mapper = session.getMapper(UserMapper.class);
        UserEntity entity = mapper.findByPrimaryKey(1L);
        System.out.println(entity.getEmail()==null?"null":entity.getPhone());
        entity.setUserName("查询再更新");
        mapper.updateUser(entity);
        session.commit();
    }catch (Exception e){
        e.printStackTrace();
    }finally{
        if(session!= null){
            session.close();
        }
    }
}

@Test
    public void test(){
        String resource = "mybatis-config.xml";
        SqlSessionFactory sqlSessionFactory=null;
        SqlSession session = null;
        try {
            InputStream in = new ClassPathResource(resource).getInputStream();
            //创建单例工厂
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
            session = sqlSessionFactory.openSession();
            XmlUserDao xmlMybatisTest = new XmlUserDao();
            UserMapper mapper = session.getMapper(UserMapper.class);

            UserEntity entity = new UserEntity();
            entity.setId(1L);
            entity.setUserName("只更新");


            mapper.updateUser(entity);


            session.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void test1(){
        Integer a = -128;
        Integer b = -128;
        System.out.println(a == b);
    }
}
