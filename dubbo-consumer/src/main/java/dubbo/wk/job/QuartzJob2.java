package dubbo.wk.job;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @decription  没有基类的定时任务了  根据 targetObject  targetMethod调用任务方法
 * Created by wgp on 2018/6/19.
 */
public class QuartzJob2 {

    public void  run(){
        LocalDateTime local = LocalDateTime.now();
        String s = local.format(DateTimeFormatter.ofPattern("HH:mm:ss", Locale.CHINESE));
        System.out.println(s + "——没有基类的任务执行类"  );
    }

}
