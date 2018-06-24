package dubbo.wk.dao;

import dubbo.wk.model.domain.UserEntity;

/**
 * @author WGP
 * @descriptionparent
 * @date 2018/4/30
 **/
public interface UserMapper {
    UserEntity findByPrimaryKey(Long id);
    void insert(UserEntity user);
}
