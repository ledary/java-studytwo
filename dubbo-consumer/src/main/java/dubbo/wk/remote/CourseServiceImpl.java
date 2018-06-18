package dubbo.wk.remote;

import com.alibaba.dubbo.config.annotation.Service;
import dubbo.wk.model.DemoRemoteModel;
import dubbo.wk.model.domain.CourseEntity;
import dubbo.wk.service.CourseService;
import dubbo.wk.service.IDemoService;

/**
 * @author WGP
 * @descriptionparent
 * @date 2018/4/30
 **/
@org.springframework.stereotype.Service
public class CourseServiceImpl implements CourseService {

    @com.alibaba.dubbo.config.annotation.Reference
    private IDemoService iDemoService;
    @Override
    public CourseEntity findCourseByPrimeryKey(Long id) {
        return null;
    }

    @Override
    public DemoRemoteModel findDemoModel() {
       return iDemoService.findDemoModel();
    }
}
