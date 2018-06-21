package dubbo.wk.controller;

import dubbo.wk.constants.Constant;
import dubbo.wk.model.ExcelModel;
import dubbo.wk.utils.excel.ExcelUtils;
import dubbo.wk.utils.Result;
import dubbo.wk.utils.ZipFileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @decription  文件controller  导出，导入，PDF
 * Created by wgp on 2018/6/20.
 */

@Controller
@RequestMapping("file")
public class FileController {
    private Logger logger = LoggerFactory.getLogger(FileController.class);

    @Value("temp")
    private String tempFile;


    /**
     * 以文件方式生成Excel
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "exportExcel",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result exportFile(HttpServletRequest request, HttpServletResponse response){
        Result result = new Result();
        List<ExcelModel> list = new ArrayList<>(64);
        for(int i=0;i<20;i++){
            ExcelModel excelModel = new ExcelModel();
            excelModel.setAgreementNumber("agree" + i);
            excelModel.setCustomsNumber("customs" + i);
            excelModel.setEnterpriseNumber("enter" + i);
            String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            excelModel.setDeclareDate(date);
            excelModel.setPassDate(date);
            excelModel.setRecordNumber("record" + i);
            excelModel.setTradeWay("FOB" + i);
            excelModel.setTransportWay("油罐运输" + i);
            list.add(excelModel);
        }

        try{
            HSSFWorkbook workbook = ExcelUtils.makeExcel(list,"测试Sheet",response,false);


            //获取临时目录
            String path = Thread.currentThread().getContextClassLoader().getResource("").getPath().toString() + File.separator + tempFile;
            //生成Excel名称
            String excelName =path + File.separator +  "Excel" + System.currentTimeMillis() + ".xls";

            //创建Excel文件
            File excelFile = new File(excelName);
            //写入Excel数据流
            OutputStream excelOut =new  FileOutputStream(excelFile);
            workbook.write(excelOut);

            //生成Excel 集合，集合里存放Excel的绝对路径
            List<String> filesList = new ArrayList<>();
            filesList.add(excelFile.getAbsolutePath());

            //创建压缩包
            File zipFile = new File(path+ "temp.zip");
            //压缩工具类，生成压缩包文件
            ZipFileUtils.zipFile(filesList,zipFile);

            //重置response输出流
            response.reset();
//            response.setContentType("application/octet-stream;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(zipFile.getName().getBytes("gb2312"), "ISO8859-1"));

            //把zip文件的数据 放入response输出流
            OutputStream responseOut = response.getOutputStream();
            FileInputStream inStream = new FileInputStream(zipFile);
            byte[] buf = new byte[4096];
            int readLength;
            while (((readLength = inStream.read(buf)) != -1)) {
                responseOut.write(buf, 0, readLength);
            }

            //关闭所有的输入  输出流
            inStream.close();
            excelOut.close();
            responseOut.close();

            //删除临时文件
            excelFile.delete();
            zipFile.delete();

            result.setObj(true);
           result.setCode(Constant.SUCCESS_CODE);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return result;
        }
    }


    /**
     * 以字节方式生成Excel  打包
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "exportExcel2",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result exportFile2(HttpServletRequest request, HttpServletResponse response){
        Result result = new Result();
        List<ExcelModel> list = new ArrayList<>(64);
        for(int i=0;i<20;i++){
            ExcelModel excelModel = new ExcelModel();
            excelModel.setAgreementNumber("agree" + i);
            excelModel.setCustomsNumber("customs" + i);
            excelModel.setEnterpriseNumber("enter" + i);
            String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            excelModel.setDeclareDate(date);
            excelModel.setPassDate(date);
            excelModel.setRecordNumber("record" + i);
            excelModel.setTradeWay("FOB" + i);
            excelModel.setTransportWay("油罐运输" + i);
            list.add(excelModel);
        }

        try{
            HSSFWorkbook workbook = ExcelUtils.makeExcel(list,"测试Sheet",response,false);

            //获取临时目录
            String path = Thread.currentThread().getContextClassLoader().getResource("").getPath().toString() + File.separator + tempFile;
            //创建压缩包
            File zipFile = new File(path + File.separator + "temp.zip");
            //压缩工具类，生成压缩包文件
            ZipFileUtils.zipFile(workbook.getBytes(),zipFile,"Excel1.xls");

            //重置response输出流
            response.reset();
            response.setContentType("application/octet-stream;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(zipFile.getName().getBytes("gb2312"), "ISO8859-1"));

            //把zip文件的数据 放入response输出流
            OutputStream responseOut = response.getOutputStream();
            FileInputStream inStream = new FileInputStream(zipFile);
            byte[] buf = new byte[4096];
            int readLength;
            while (((readLength = inStream.read(buf)) != -1)) {
                responseOut.write(buf, 0, readLength);
            }
            //关闭所有的输入  输出流
            inStream.close();
            responseOut.close();
            //删除临时文件
            zipFile.delete();

            result.setObj(true);
            result.setCode(Constant.SUCCESS_CODE);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return result;
        }
    }

}
