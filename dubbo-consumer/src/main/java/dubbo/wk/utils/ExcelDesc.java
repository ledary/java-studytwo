package dubbo.wk.utils;

import java.lang.annotation.*;

/**
 * @decription  针对字段 运行时起作用
 * Created by wgp on 2018/6/20.
 */


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ExcelDesc {
    //标题
    String value();
    //列的序号
    String orderBy();

    boolean display() default  true;

}
