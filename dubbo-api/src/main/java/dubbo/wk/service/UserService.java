package dubbo.wk.service;

import dubbo.wk.model.domain.UserEntity;

/**
 * @author WGP
 * @descriptionparent
 * @date 2018/4/30
 **/
public interface UserService {
    UserEntity findUserByPrimaryKey(Long id);
}
