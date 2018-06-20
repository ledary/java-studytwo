package dubbo.wk.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @description   基于基类的任务类
 * Created by wgp on 2018/6/19.
 */
public class QuartzJob1 extends QuartzJobBean {

    private Integer timeout;
    public void setTimeout(Integer timeout){
        this.timeout = timeout;
    }
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("quartz开始执行了？基于基类 QyartzJobBean的任务类");
    }
}
