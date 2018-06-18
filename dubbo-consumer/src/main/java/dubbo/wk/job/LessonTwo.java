package dubbo.wk.job;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * @author WGP
 * @descriptionparent
 * @date 2018/6/17
 **/
public class LessonTwo {

    public void run() throws Exception{
        SchedulerFactory factory = new StdSchedulerFactory();
        Scheduler sched = factory.getScheduler();

        Date startTime = DateBuilder.nextGivenSecondDate(null,15);
        JobDetail job = JobBuilder.newJob(TwoJob.class).withIdentity("job1","group1").build();
        SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder.newTrigger().withIdentity("trigger","group1").startAt(startTime).build();



        Date ft = sched.scheduleJob(job,trigger);
        System.out.println(job.getKey() + " will run at: " + ft + " and repeat "
                + trigger.getRepeatCount() + " times, every "
                + trigger.getRepeatInterval() / 1000 + " seconds");


        job = JobBuilder.newJob().withIdentity("job2","group1").build();
        trigger = (SimpleTrigger)TriggerBuilder.newTrigger().withIdentity("trigger2","group1").build();
        ft = sched.scheduleJob(job,trigger);
        System.out.println(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
                + trigger.getRepeatInterval() / 1000 + " seconds");

//        withIntervalInSeconds(10)  意思是说每10秒执行一次，
        //withRepeatCount（10） 重复10次，执行一次，总共执行11次
        job = JobBuilder.newJob().withIdentity("job3","group1").build();
        trigger = (SimpleTrigger) TriggerBuilder.newTrigger().withIdentity("trigger3","group1")
                .startAt(startTime).withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).withRepeatCount(10)).build();
        ft = sched.scheduleJob(job,trigger);
        System.out.println(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
                + trigger.getRepeatInterval() / 1000 + " seconds");


          //      startAt(futureDate(5,IntervalUnit.MINUTE)  在未来5分钟后执行一次
//        trigger = (SimpleTrigger) newTrigger().withIdentity("trigger5", "group1")
//                .startAt(futureDate(5, IntervalUnit.MINUTE)).build();


        /* 无限执行下去，每隔40秒执行一次
        trigger = newTrigger().withIdentity("trigger6", "group1").startAt(startTime)
                .withSchedule(simpleSchedule().withIntervalInSeconds(40).repeatForever()).build();
        */


        /* 重复执行20次  每隔5分钟执行一次
         trigger = newTrigger().withIdentity("trigger7", "group1").startAt(startTime)
        .withSchedule(simpleSchedule().withIntervalInMinutes(5).withRepeatCount(20)).build();
         */


        /*这个任务直接执行 无需触发器来触发
        // jobs can be fired directly... (rather than waiting for a trigger)
        job = newJob(SimpleJob.class).withIdentity("job8", "group1").storeDurably().build();
        sched.addJob(job, true);
        log.info("'Manually' triggering job8...");
        sched.triggerJob(jobKey("job8", "group1"));
        */
    }

}
