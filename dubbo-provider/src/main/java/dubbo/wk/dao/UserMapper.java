package dubbo.wk.dao;

import dubbo.wk.model.domain.UserEntity;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

/**
 * @author WGP
 * @descriptionparent
 * @date 2018/4/30
 **/
public interface UserMapper {
    UserEntity findByPrimaryKey(Long id);
    void insert(UserEntity user);
    void updateUser(UserEntity user);

    void updateUserByDate(UserEntity user);

    Map<String,UserEntity> selectUserList();
    List<UserEntity> getList(Map map);
}
