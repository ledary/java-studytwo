package dubbo.wk.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author WGP
 * @description  定时任务测试类
 * @date 2018/6/18
 **/
@Component
public class TimerTask {

    @Scheduled(cron = "* * * * * ?")
    public void taskTest(){
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

}
