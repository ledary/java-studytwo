package dubbo.wk.job.quartzexample;

import dubbo.wk.utils.DataFormatUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

import java.util.Date;

/**
 * @author WGP
 * @descriptionparent
 * @date 2018/6/17
 **/
public class TwoJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobKey jobKey = context.getJobDetail().getKey();
        System.out.println("SimpleJob says: " + jobKey + " executing at " + DataFormatUtils.formatShort(new Date()));



    }
}
