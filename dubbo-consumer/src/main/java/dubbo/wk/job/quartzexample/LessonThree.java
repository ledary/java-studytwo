package dubbo.wk.job.quartzexample;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.TriggerBuilder;

/**
 * @author WGP
 * @descriptionparent
 * @date 2018/6/17
 **/
public class LessonThree {

    public void run()throws Exception{

        //每2分钟执行一次
        CronTrigger trigger = TriggerBuilder
                .newTrigger().withIdentity("trigger1","group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * * ?")).build();

    }
}
