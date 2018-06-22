package dubbo.wk.utils;

import dubbo.wk.model.OrderEmailModel;
import dubbo.wk.model.WordTemplateModel;
import dubbo.wk.service.FileService;
import dubbo.wk.service.impl.FileServiceImpl;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.junit.Test;

import java.io.*;
import java.util.Properties;

/**
 * @author WGP
 * @descriptionparent
 * @date 2018/6/16
 **/

public class VelocityUtils {

//    Velocity是单一实例。放到静态代码块里
    static{
        Properties p = new Properties();
        p.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(p);
    }


    /*
    创建一个新的运行实例 RuntimeInstance ，
    每个velocity引擎都会拥有各自的实例，互不干扰。
     */
    @Test
    public void test0() {
        Properties p = new Properties();
        p.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
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
        VelocityContext context = new VelocityContext();
        context.put("model", model);
        StringWriter w = new StringWriter();
        Velocity.mergeTemplate(  tplName, "UTF-8", context, w);
        return w.toString();
    }


    public <T> void  velocityTemplate(T t, String tplName,Writer writer) {
        VelocityContext context = new VelocityContext();
        context.put("model", t);
        Template template = Velocity.getTemplate( tplName,"utf-8");
        template.merge(context,writer);
    }

    public static void main(String[] args)throws Exception {

        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath().toString();
        String fileNam  = path + File.separator + "vm" + File.separator + "word.vm";
        StringWriter writer = new StringWriter();
       VelocityUtils utils = new VelocityUtils();
       FileService service = new FileServiceImpl();
        WordTemplateModel wordTemplateModel = service.queryWordTemplate();
       utils.velocityTemplate(wordTemplateModel,"word.vm",writer);
        System.out.println(writer.toString());



    }
}
