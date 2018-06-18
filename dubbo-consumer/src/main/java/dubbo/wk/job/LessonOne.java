package dubbo.wk.job;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author WGP
 * @description  quartz  第一节课
 * @date 2018/6/17
 **/
public class LessonOne {

    public void test0()throws Exception{
        //创建 定时器的实例
        SchedulerFactory factory = new StdSchedulerFactory();
        Scheduler sched = factory.getScheduler();
        //采用 import  static方式，可以直接使用evenMinuteDate方法
        //获取下一分钟的时间 整点时间 2018-6-17 15:30:00
        Date runTime = DateBuilder.evenMinuteDate(new Date());

        //定义一个job关联到HelloJbo类
        JobDetail job = JobBuilder.newJob(HelloJob.class)
                .withIdentity("job1","group1").build();
        //任务将在下一分钟时刻引发 比如：  2018-6-17 15:30:00
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1","group1").startAt(runTime).build();
        sched.scheduleJob(job,trigger);
        System.out.println(job.getKey() + " will run at: " + runTime );

        sched.start();
        try{
            Thread.sleep(65L * 1000L);
        }catch (Exception e){

        }

        sched.shutdown();



    }

//    public class HelloJob implements Job{
//        public HelloJob(){}
//        @Override
//        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//            System.out.println("Hello World " + LocalDateTime.now()
//                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//        }
//    }

    public static void main(String[] args) throws Exception{
        LessonOne one = new LessonOne();
        one.test0();
    }
}
