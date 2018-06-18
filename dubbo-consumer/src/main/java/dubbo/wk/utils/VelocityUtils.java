package dubbo.wk.utils;

import dubbo.wk.model.OrderEmailModel;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.junit.Test;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.Properties;

/**
 * @author WGP
 * @descriptionparent
 * @date 2018/6/16
 **/

public class VelocityUtils {
    private static final  String path="vm";



    /*
    创建一个新的运行实例 RuntimeInstance ，
    每个velocity引擎都会拥有各自的实例，互不干扰。
     */


    @Test
    public void test0() {
        Properties p = new Properties();
//        p.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        p.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
//        VelocityEngine velocity = new VelocityEngine(p);
        VelocityEngine velocity = new VelocityEngine(p);
        VelocityContext context = new VelocityContext();
        context.put("name", "czy");
        Template template = velocity.getTemplate("vm/testtemplate.vm");
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        template.merge(context, writer);
        try {
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void test1() {
        Properties p = new Properties();
//        p.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        p.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
//        VelocityEngine velocity = new VelocityEngine(p);
        Velocity.init(p);
        VelocityContext context = new VelocityContext();
        context.put("name", "武刚鹏");
        context.put("project", "Velocity代码");
        StringWriter w = new StringWriter();
        Velocity.mergeTemplate("vm\\testtemplate.vm", "UTF-8", context, w);
        System.out.println(w);
        String s = "We are using $project $name to render this.";
        w = new StringWriter();
        Velocity.evaluate(context, w, "mysring", s);
        System.out.println("string：" + w);
    }

    public String velocityContent(OrderEmailModel model,String tplName) {
//        properties可以使用spring加载properties文件
        Properties p = new Properties();
        p.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(p);
        VelocityContext context = new VelocityContext();
        context.put("model", model);
        StringWriter w = new StringWriter();
//        path ="vm"   所有的模板文件放在 resource/vm文件夹下
        Velocity.mergeTemplate(path + File.separator + tplName, "UTF-8", context, w);
        return w.toString();
    }
}
