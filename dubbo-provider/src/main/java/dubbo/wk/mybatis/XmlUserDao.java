package dubbo.wk.mybatis;

import dubbo.wk.dao.UserMapper;
import dubbo.wk.model.domain.UserEntity;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;

/**
 * 编程式 使用mybatis 测试事物级别
 * Created by wgp on 2018/6/24.
 */
public class XmlUserDao {

    public static void main(String[] args) {
        String resource = "mybatis-config.xml";
        SqlSessionFactory sqlSessionFactory=null;
        SqlSession session = null;
        try{
            InputStream in = new ClassPathResource(resource).getInputStream();
            //创建单例工厂
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
            session = sqlSessionFactory.openSession(false);
            XmlUserDao xmlMybatisTest = new XmlUserDao();
            UserMapper mapper = session.getMapper(UserMapper.class);
            UserEntity entity = mapper.findByPrimaryKey(1L);
            System.out.println(entity.getEmail()==null?"null":entity.getEmail());

            UserEntity entity1 = mapper.findByPrimaryKey(1L);
            System.out.println(entity.getEmail()==null?"":entity.getEmail());
//            session.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            if(session!= null){
                session.close();
            }
        }
    }



    public void insertUserEntitty(){
        String resource = "mybatis-config.xml";
        SqlSessionFactory sqlSessionFactory=null;
        SqlSession session = null;
        try {
            InputStream in = new ClassPathResource(resource).getInputStream();
            //创建单例工厂
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
            session = sqlSessionFactory.openSession(false);
            XmlUserDao xmlMybatisTest = new XmlUserDao();
            UserMapper mapper = session.getMapper(UserMapper.class);
            UserEntity entity = new UserEntity();
            entity.setUserName("测试姓名");
            entity.setEmail("1111111qq.com");
            entity.setPhone("123456789");
            entity.setPassword("12334444");
            entity.setSex("男");
            mapper.insert(entity);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
