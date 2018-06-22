package dubbo.wk.utils;


import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import dubbo.wk.model.PdfTemplateModel;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Iterator;

/**
 * Created by wgp on 2018/6/22.
 */
public class PdfUtils {



    public static <T> void makePdf(T t,String tplName,OutputStream out){
        InputStream inputStream = new  ClasspathResourceLoader().getResourceStream(tplName);
        try{
            //处理中文问题
            PdfFont font = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
            PdfWriter writer = new PdfWriter(out);
            PdfReader reader = new PdfReader(inputStream);
            PdfDocument pdf = new PdfDocument(reader,writer);
            com.itextpdf.forms.PdfAcroForm form =
                    com.itextpdf.forms.PdfAcroForm.getAcroForm(pdf,true);
            Field[] fields = PdfTemplateModel.class.getDeclaredFields();
            for (Field field:fields) {
                field.setAccessible(true);
                String name = field.getName();
                String value = field.get(t).toString();
                System.out.println(name + "：" + value);
                form.getField(name).setValue(value).setFontSize(10).setFont(font);
            }
        form.flattenFields();
            pdf.close();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }
}

