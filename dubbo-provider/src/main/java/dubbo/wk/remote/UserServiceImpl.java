package dubbo.wk.remote;

import dubbo.wk.dao.UserMapper;
import dubbo.wk.model.domain.UserEntity;
import dubbo.wk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author WGP
 * @descriptionparent
 * @date 2018/4/30
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public UserEntity findUserByPrimaryKey(Long id) {
        return userMapper.findByPrimaryKey(id);
    }
}
