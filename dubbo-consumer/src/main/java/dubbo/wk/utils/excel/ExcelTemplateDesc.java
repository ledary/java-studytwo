package dubbo.wk.utils.excel;

import java.lang.annotation.*;

/**
 * Created by wgp on 2018/6/22.
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ExcelTemplateDesc {
    String attribute();
}
