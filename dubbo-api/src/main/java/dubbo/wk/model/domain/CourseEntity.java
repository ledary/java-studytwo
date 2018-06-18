package dubbo.wk.model.domain;

import java.util.Date;

/**
 * @author WGP
 * @descriptionparent
 * @date 2018/4/30
 **/
public class CourseEntity extends BaseEntity {
    private String courseName;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
