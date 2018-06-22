package dubbo.wk.controller;

import dubbo.wk.constants.Constant;
import dubbo.wk.model.ExcelTemplateModel;
import dubbo.wk.model.ExcelModel;
import dubbo.wk.model.WordTemplateModel;
import dubbo.wk.service.FileService;
import dubbo.wk.utils.VelocityUtils;
import dubbo.wk.utils.excel.ExcelUtils;
import dubbo.wk.utils.Result;
import dubbo.wk.utils.ZipFileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
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

    @Value("${file.temp}")
    private String tempFile;

    @Value("${excel.template}")
    private String excelTemplate;

    @Value("${word.template}")
    private String wordTemplate;

    @Value("${excel.xls.template}")
    private String xlsTemplate;

    @Value("${vm.file}")
    private  String vmFile;

    @Autowired
    private FileService fileService;


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
            logger.error("導出Excel失敗",e);
        }finally {
            return result;
        }
    }

    //导入
    @RequestMapping(value = "importExcel",method = RequestMethod.POST)
    @ResponseBody
    public Result importExcel(@RequestParam("files")List<MultipartFile> files){
        Result result = new Result();
        result.setCode(Constant.FAIL_CODE);
        result.setMessage("失败");
        result.setObj(true);

        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath().toString() + File.separator + tempFile;
        try{
            ExcelUtils<ExcelModel> excelUtils = new ExcelUtils<>(ExcelModel.class);
            try{
                for(MultipartFile file:files){
                    List<ExcelModel> list = excelUtils.importExcel(file.getOriginalFilename(),null,file.getInputStream());
                }
                result.setObj(true);
                result.setMessage("成功");
                result.setCode(Constant.SUCCESS_CODE);
            }catch (IOException ioe){
                ioe.printStackTrace();
                logger.error("获取文件流异常",ioe);
            }
        }catch (Exception ex){
            logger.error("导出Excel失败",ex);
        }finally {
            return result;
        }



    }




    //根据Excel导出模板
    @RequestMapping(value = "exportExcelByTemplate",method = RequestMethod.GET)
    @ResponseBody
    public void exportExcelByTemplate(HttpServletResponse response){
        Result result = new Result();
        result.setCode(Constant.FAIL_CODE);
        result.setMessage("失败");
        result.setObj(true);
        VelocityUtils velocityUtils = new VelocityUtils();
        ExcelTemplateModel excelTemplateModel = fileService.queryExcelTemplate();

        StringWriter writer = null;

        try{
            writer = new StringWriter();
            response.setHeader("Content-disposition", "attachment; filename=\"" + URLEncoder.encode("xxx.xls", "utf-8") + "\"");
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            OutputStream os = response.getOutputStream();
            String path = Thread.currentThread().getContextClassLoader().getResource("").getPath().toString() + File.separator + tempFile;
            velocityUtils.velocityTemplate(excelTemplateModel,excelTemplate,writer);
            System.out.println(writer.getBuffer().toString());
            os.write(writer.getBuffer().toString().getBytes("utf-8"));
            writer.close();
            os.close();
        }catch (IOException ioe){
            logger.error("writer输出流异常",ioe);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("异常",e);
        }

    }





    //根据Word模板导出数据
    @RequestMapping(value = "exportWordByTemplate",method = RequestMethod.GET)
    @ResponseBody
    public void exportWordByTemplate(HttpServletResponse response,HttpServletRequest request){
        Result result = new Result();
        result.setCode(Constant.FAIL_CODE);
        result.setMessage("失败");
        result.setObj(true);
        VelocityUtils velocityUtils = new VelocityUtils();
        WordTemplateModel model = fileService.queryWordTemplate();


        try{
            response.setHeader("Content-disposition", "attachment; filename=\"" + URLEncoder.encode("xxx.doc", "utf-8") + "\"");
            response.setContentType("application/msword;charset=UTF-8");
            StringWriter writer  = new StringWriter();
            String path = Thread.currentThread().getContextClassLoader().getResource("").getPath().toString() + File.separator + tempFile;
            velocityUtils.velocityTemplate(model,wordTemplate,writer);
//            System.out.println(writer.getBuffer().toString());
//            os.write(writer.getBuffer().toString().getBytes("utf-8"));
            OutputStream out = response.getOutputStream();
          out.write(writer.getBuffer().toString().getBytes("utf-8"));
          writer.close();
          out.close();
        }catch (IOException ioe){
            logger.error("writer输出流异常",ioe);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("异常",e);
        }

    }



    @RequestMapping(value = "exportByXls",method = RequestMethod.GET)
    public void exportExcelByXlsTemplate(HttpServletResponse response){
        try{
            response.setHeader("Content-disposition", "attachment; filename=\"" + URLEncoder.encode("xxx.xls", "utf-8") + "\"");
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            OutputStream os = response.getOutputStream();
            ExcelTemplateModel model = fileService.queryExcelTemplateByXls();
            ExcelUtils<ExcelTemplateModel> utils = new ExcelUtils<>(ExcelTemplateModel.class);
            utils.makeExcelByXlsTemplate(model,vmFile + File.separator + xlsTemplate,os);
           os.flush();
           os.close();

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

}

//
//    /**
//     * 以字节方式生成Excel  打包  这种方式有问题
//     * @param request
//     * @param response
//     * @return
//     */
//    @RequestMapping(value = "exportExcel2",method = RequestMethod.GET)
//    @ResponseBody
//    public Result exportFile2(HttpServletRequest request, HttpServletResponse response){
//        Result result = new Result();
//        List<ExcelModel> list = new ArrayList<>(64);
//        for(int i=0;i<20;i++){
//            ExcelModel excelModel = new ExcelModel();
//            excelModel.setAgreementNumber("agree" + i);
//            excelModel.setCustomsNumber("customs" + i);
//            excelModel.setEnterpriseNumber("enter" + i);
//            String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//            excelModel.setDeclareDate(date);
//            excelModel.setPassDate(date);
//            excelModel.setRecordNumber("record" + i);
//            excelModel.setTradeWay("FOB" + i);
//            excelModel.setTransportWay("油罐运输" + i);
//            list.add(excelModel);
//        }
//
//        try{
//            HSSFWorkbook workbook = ExcelUtils.makeExcel(list,"测试Sheet",response,false);
//
//            //获取临时目录
//            String path = Thread.currentThread().getContextClassLoader().getResource("").getPath().toString() + File.separator + tempFile;
//            //创建压缩包
//            File zipFile = new File(path + File.separator + "temp.zip");
//            //压缩工具类，生成压缩包文件
//            ZipFileUtils.zipFile(workbook.getBytes(),zipFile,"Excel1.xls");
//
//            //重置response输出流
//            response.reset();
//            response.setContentType("application/octet-stream;charset=utf-8");
//            response.setHeader("Content-Disposition", "attachment;filename=" + new String(zipFile.getName().getBytes("gb2312"), "ISO8859-1"));
//
//            //把zip文件的数据 放入response输出流
//            OutputStream responseOut = response.getOutputStream();
//            FileInputStream inStream = new FileInputStream(zipFile);
//            byte[] buf = new byte[4096];
//            int readLength;
//            while (((readLength = inStream.read(buf)) != -1)) {
//                responseOut.write(buf, 0, readLength);
//            }
//            //关闭所有的输入  输出流
//            inStream.close();
//            responseOut.close();
//            //删除临时文件
//            zipFile.delete();
//
//            result.setObj(true);
//            result.setCode(Constant.SUCCESS_CODE);
//        }catch (Exception e){
//            e.printStackTrace();
//            logger.error("导出Excel失败",e);
//        }finally {
//            return result;
//        }
//    }