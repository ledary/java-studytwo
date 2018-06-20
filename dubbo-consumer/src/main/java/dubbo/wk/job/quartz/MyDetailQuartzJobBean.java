package dubbo.wk.job.quartz;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


/**
 * @decription quartz分布式集群 工厂类
 * Created by wgp on 2018/6/19.
 */

/*
@PersistJobDataAfterExecution  添加到 Job 类后，表示 Quartz 将会在成功执行 execute() 方法后（没有抛出异常）更新 JobDetail 的 JobDataMap，下一次执行相同的任务（JobDetail）将会得到更新后的值，而不是原始的值。就像@DisallowConcurrentExecution 一样，这个注释基于 JobDetail 而不是 Job 类的实例。
@DisallowConcurrentExecution   不允许并发执行
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Component
public class MyDetailQuartzJobBean extends QuartzJobBean implements ApplicationContextAware {
    private static final Logger  logger = LoggerFactory.getLogger(MyDetailQuartzJobBean.class);
    private static ApplicationContext ctx;

    @Override
    public  void setApplicationContext(ApplicationContext applicationContext) {
        ctx = applicationContext;
    }

    private String targetObject;
    private String targetMethod;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try{
            Object otargetObject = ctx.getBean(targetObject);
            Method m = otargetObject.getClass().getMethod(targetMethod);
            m.invoke(otargetObject);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("定时器异常Bean",e);
        }
    }


    public void setTargetObject(String targetObject) {
        this.targetObject = targetObject;
    }

    public void setTargetMethod(String targetMethod) {
        this.targetMethod = targetMethod;
    }
}
