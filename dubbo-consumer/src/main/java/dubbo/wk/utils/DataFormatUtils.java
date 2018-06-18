package dubbo.wk.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author WGP
 * @descriptionparent
 * @date 2018/6/17
 **/
public class DataFormatUtils {



    public static String formatShort(Date date){
          SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
          return format.format(date);
    }
    public static String formatLong(Date date,String pattern){
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }
}
