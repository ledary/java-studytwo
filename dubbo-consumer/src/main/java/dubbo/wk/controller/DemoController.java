package dubbo.wk.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import dubbo.wk.model.DemoRemoteModel;
import dubbo.wk.service.CourseService;
import dubbo.wk.service.IDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author WGP
 * @descriptionparent
 * @date 2018/4/29
 **/
//@Controller
public class DemoController {

    @Autowired
  private CourseService courseService;


}
