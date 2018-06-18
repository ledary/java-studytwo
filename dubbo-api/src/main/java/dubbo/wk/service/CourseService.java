package dubbo.wk.service;

import dubbo.wk.model.DemoRemoteModel;
import dubbo.wk.model.domain.CourseEntity;

/**
 * @author WGP
 * @descriptionparent
 * @date 2018/4/30
 **/
public interface CourseService {
    CourseEntity findCourseByPrimeryKey(Long id);


    DemoRemoteModel findDemoModel();
}
