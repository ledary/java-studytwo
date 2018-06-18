package dubbo.wk.remote;

import dubbo.wk.dao.UserMapper;
import dubbo.wk.model.DemoRemoteModel;
import dubbo.wk.model.domain.UserEntity;
import dubbo.wk.service.IDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.UUID;

/**
 * @author WGP
 * @descriptionparent
 * @date 2018/4/22
 **/

@com.alibaba.dubbo.config.annotation.Service
public class DemoServiceImpl implements IDemoService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public DemoRemoteModel findDemoModel() {
        DemoRemoteModel model = new DemoRemoteModel();
        model.setId(1L);
        UserEntity userEntity = userMapper.findByPrimaryKey(1L);
        model.setUserName(userEntity.getUserName());
        model.setId(userEntity.getId());
        return model;
    }
}
