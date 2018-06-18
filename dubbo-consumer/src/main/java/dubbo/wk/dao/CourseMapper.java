package dubbo.wk.dao;

import dubbo.wk.model.domain.CourseEntity;

/**
 * @author WGP
 * @descriptionparent
 * @date 2018/5/1
 **/
public interface CourseMapper {
    CourseEntity findCourseByPrimeryKey(Long id);
}
