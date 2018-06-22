package dubbo.wk.utils.excel;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.sun.org.apache.xml.internal.resolver.readers.TR9401CatalogReader;
import dubbo.wk.model.ExcelTemplateModel;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wgp on 2018/6/20.
 */
public class ExcelUtils<T> {

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    Class<T> clazz;
    public ExcelUtils(Class<T> clazz){
        this.clazz = clazz;
    }


    public List<Field> getMappedFiled(Class clazz,List<Field> fields){
        //检测字段
        if(fields == null){
            fields = new ArrayList<>();
        }
        //直接获取Excel描述的字段
        Field[] allFields = clazz.getDeclaredFields();
        for (Field field:allFields) {
            if(field.isAnnotationPresent(ExcelDesc.class)){
                fields.add(field);
            }
        }
        //递归获取父类的Excel描述字段，排除Object类型
        if(clazz.getSuperclass() != null && !clazz.getSuperclass().equals(Object.class)){
            getMappedFiled(clazz.getSuperclass(),fields);
        }
        return fields;
    }

    public List<T> importSheet(int rows, Sheet sheet) throws IllegalAccessException, InstantiationException {
        int maxCol = 0;
        List<T> list = new ArrayList<>();
        // Field[] allFields = clazz.getDeclaredFields();// 得到类的所有field.
        List<Field> allFields = getMappedFiled(clazz, null);

        // 定义一个map用于存放列的序号和field.
        Map<Integer, Field> fieldsMap = new HashMap<>(16);
        for (Field field : allFields) {
            // 将有注解的field存放到map中.
            if (field.isAnnotationPresent(ExcelDesc.class)) {
                ExcelDesc attr = field.getAnnotation(ExcelDesc.class);
                int col = Integer.valueOf(attr.orderBy());
                maxCol = Math.max(col, maxCol);
                // 设置类的私有字段属性可访问.
                field.setAccessible(true);
                fieldsMap.put(col, field);
            }
        }
        // 从第2行开始取数据,默认第一行是表头.
        for (int i = 1; i < rows; i++) {
            //获取行
            Row row = sheet.getRow(i);
            // int cellNum = row.getPhysicalNumberOfCells();
            // int cellNum = row.getLastCellNum();
            T entity = null;
            //循环列，获取单元格
            for (int j = 0; j < maxCol; j++) {
                Cell cell = row.getCell(j);
                if (cell == null) {
                    continue;
                }
                // int cellType = cell.getCellType();
                CellType cellType = cell.getCellTypeEnum();
                String c = "";
                //根据不同的类型，获取数据 赋值给c
                if (cellType == CellType.NUMERIC) {
                    c = String.valueOf(cell.getNumericCellValue());
                } else if (cellType == CellType.BOOLEAN) {
                    c = String.valueOf(cell.getBooleanCellValue());
                } else {
                    c = cell.getStringCellValue();
                }
                if (c == null || ("").equals(c)) {
                    continue;
                }
                // 如果不存在实例则新建.
                entity = (entity == null ? clazz.newInstance() : entity);
                // 从map中得到对应列的field.
                Field field = fieldsMap.get(j + 1);
                if (field == null) {
                    continue;
                }
                // 取得类型,并根据对象类型设置值.
                Class<?> fieldType = field.getType();
                //根据不同的类型，赋值为不同的数据。赋值给entity
                // 缺少对日期类型的处理
                if (String.class == fieldType) {
                    field.set(entity, String.valueOf(c));
                } else if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {
                    field.set(entity, Integer.parseInt(c));
                } else if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
                    field.set(entity, Long.valueOf(c));
                } else if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
                    field.set(entity, Float.valueOf(c));
                } else if ((Short.TYPE == fieldType) || (Short.class == fieldType)) {
                    field.set(entity, Short.valueOf(c));
                } else if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {
                    field.set(entity, Double.valueOf(c));
                } else if (Character.TYPE == fieldType) {
                    if ((c != null) && (c.length() > 0)) {
                        field.set(entity, c.charAt(0));
                    }
                }

            }
            if (entity != null) {
                list.add(entity);
            }
        }
        return list;
    }
    /**
     * datas为导出的数据
     * fileName为excel文件名称
     * sheetName为sheet名称
     * 实体类注解通用导出方法
     *displayAll  好像是冻结首列的参数
     * @param datas
     * @param fileName
     * @param sheetName
     */
    public static <T> void exportExcel(List<T> datas, String fileName, String sheetName, HttpServletResponse response, boolean displayAll) throws IOException, IllegalAccessException {
//        HSSFWorkbook 2003版本之前的

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);
        //创建第一行
        HSSFRow row = sheet.createRow(0);

        //设置单元格为文本格式
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        HSSFDataFormat format = workbook.createDataFormat();
        cellStyle.setDataFormat(format.getFormat("@"));


        Boolean isHasTitle = false;

        for (int i = 0; i < datas.size(); i++) {
            //因为第一行是标题，所以 要 i+1
            HSSFRow rowBatch = sheet.createRow(i + 1);
            Object o = datas.get(i);
            if (!isHasTitle) {
                for (Field field : o.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    //只对含有Excel描述的字段做处理
                    if (field.isAnnotationPresent(ExcelDesc.class)) {
                        //获取字段注解
                        ExcelDesc columnConfig = field.getAnnotation(ExcelDesc.class);
                        if (displayAll) {
                            //判断是否已经获取过该code的字典数据 避免重复获取
                            HSSFCell cell = row.createCell(Integer.valueOf(columnConfig.orderBy()));
                            cell.setCellStyle(cellStyle);
                            cell.setCellValue(columnConfig.value());
                        }
                        if (!displayAll && columnConfig.display()) {
                            HSSFCell cell = row.createCell(Integer.valueOf(columnConfig.orderBy()) - 1);
                            cell.setCellStyle(cellStyle);
                            cell.setCellValue(columnConfig.value());
                        }
                    }
                }
                isHasTitle = true;
            }
            for (Field field : o.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(ExcelDesc.class)) {
                    //获取字段注解
                    ExcelDesc columnConfig = field.getAnnotation(ExcelDesc.class);
                    //displayAll  应该是冻结首列的意思
                    if (displayAll) {
                        HSSFCell cell = rowBatch.createCell(Integer.valueOf(columnConfig.orderBy()));
                        cell.setCellStyle(cellStyle);
                        //判断是否已经获取过该code的字典数据 避免重复获取
                        cell.setCellValue(field.get(o) == null ? "" : field.get(o).toString());
                        cell.setCellType(CellType.STRING);
                    }
                    if (!displayAll && columnConfig.display()) {
                        HSSFCell cell = rowBatch.createCell(Integer.valueOf(columnConfig.orderBy()) - 1);
                        cell.setCellStyle(cellStyle);
                        cell.setCellValue(field.get(o) == null ? "" : field.get(o).toString());
                        cell.setCellType(CellType.STRING);
                    }
                }
            }

        }
        // 弹出保存框方式
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            workbook.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            workbook.close();
        }
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1") + ".xls");

        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = new BufferedInputStream(is);
        BufferedOutputStream bos = new BufferedOutputStream(out);
        try {
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            if (bis != null) {
                bis.close();
            }
            if (bos != null){
                bos.close();
            }

        }

    }

    public List<T> importExcel(String fileName, String sheetName, InputStream input) throws org.apache.poi.openxml4j.exceptions.InvalidFormatException {
        int maxCol = 0;
        String xls = ".xls";
        String xlsx = ".xlsx";
        List<T> list = new ArrayList<>();
        try {
//            Workbook workbook = null;
            if(fileName == null && !fileName.contains(".")){
                return null;
            }
            Workbook workbook = WorkbookFactory.create(input);
            Sheet sheet = null;
            if ( sheetName == null || !("").equals(sheetName.trim())) {
                // 如果指定sheet名,则取指定sheet中的内容.
                sheet = workbook.getSheet(sheetName);
            }
            if (sheet == null) {
                // 如果传入的sheet名不存在则默认指向第1个sheet.
                sheet = workbook.getSheetAt(0);
            }
            int rows = sheet.getPhysicalNumberOfRows();

            if (rows > 0) {
                List<Field> allFields = getMappedFiled(clazz, null);

                // 定义一个map用于存放列的序号和field.
                Map<Integer, Field> fieldsMap = new HashMap<>(16);
                for (Field field : allFields) {
                    // 将有注解的field存放到map中.
                    if (field.isAnnotationPresent(ExcelDesc.class)) {
                        ExcelDesc attr = field.getAnnotation(ExcelDesc.class);
                        int col = Integer.valueOf(attr.orderBy());
                        maxCol = Math.max(col, maxCol);
                        // 设置类的私有字段属性可访问.
                        field.setAccessible(true);
                        fieldsMap.put(col, field);
                    }
                }
                // 从第2行开始取数据,默认第一行是表头.
                for (int i = 1; i < rows; i++) {
                    Row row = sheet.getRow(i);
                    T entity = null;
                    for (int j = 0; j < maxCol; j++) {
                        Cell cell = row.getCell(j);
                        if (cell == null) {
                            continue;
                        }
                        int cellType = cell.getCellType();
                        String c = "";
                        if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
                            c = String.valueOf(cell.getNumericCellValue());
                        } else if (cellType == HSSFCell.CELL_TYPE_BOOLEAN) {
                            c = String.valueOf(cell.getBooleanCellValue());
                        } else {
                            c = cell.getStringCellValue();
                        }
                        if (c == null || ("").equals(c)) {
                            continue;
                        }
                        // 如果不存在实例则新建.
                        entity = (entity == null ? clazz.newInstance() : entity);
                        // 从map中得到对应列的field.
                        Field field = fieldsMap.get(j + 1);
                        if (field == null) {
                            continue;
                        }
                        // 取得类型,并根据对象类型设置值.
                        Class<?> fieldType = field.getType();
                        if (String.class == fieldType) {
                            field.set(entity, String.valueOf(c));
                        } else if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {
                            field.set(entity, Integer.parseInt(c));
                        } else if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
                            field.set(entity, Long.valueOf(c));
                        } else if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
                            field.set(entity, Float.valueOf(c));
                        } else if ((Short.TYPE == fieldType) || (Short.class == fieldType)) {
                            field.set(entity, Short.valueOf(c));
                        } else if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {
                            field.set(entity, Double.valueOf(c));
                        } else if (Character.TYPE == fieldType) {
                            if ((c != null) && (c.length() > 0)) {
                                field.set(entity, c.charAt(0));
                            }
                        }

                    }
                    if (entity != null) {
                        list.add(entity);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return list;
    }



    public static <T> HSSFWorkbook makeExcel(List<T> datas, String sheetName, HttpServletResponse response, boolean displayAll) throws IOException, IllegalAccessException {
//        HSSFWorkbook 2003版本之前的

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);
        //创建第一行
        HSSFRow row = sheet.createRow(0);

        //设置单元格为文本格式
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        HSSFDataFormat format = workbook.createDataFormat();
        cellStyle.setDataFormat(format.getFormat("@"));


        Boolean isHasTitle = false;

        for (int i = 0; i < datas.size(); i++) {
            //因为第一行是标题，所以 要 i+1
            HSSFRow rowBatch = sheet.createRow(i + 1);
            Object o = datas.get(i);
            if (!isHasTitle) {
                for (Field field : o.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    //只对含有Excel描述的字段做处理
                    if (field.isAnnotationPresent(ExcelDesc.class)) {
                        //获取字段注解
                        ExcelDesc columnConfig = field.getAnnotation(ExcelDesc.class);
                        if (displayAll) {
                            //判断是否已经获取过该code的字典数据 避免重复获取
                            HSSFCell cell = row.createCell(Integer.valueOf(columnConfig.orderBy()));
                            cell.setCellStyle(cellStyle);
                            cell.setCellValue(columnConfig.value());
                        }
                        if (!displayAll && columnConfig.display()) {
                            HSSFCell cell = row.createCell(Integer.valueOf(columnConfig.orderBy()) - 1);
                            cell.setCellStyle(cellStyle);
                            cell.setCellValue(columnConfig.value());
                        }
                    }
                }
                isHasTitle = true;
            }
            for (Field field : o.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(ExcelDesc.class)) {
                    //获取字段注解
                    ExcelDesc columnConfig = field.getAnnotation(ExcelDesc.class);
                    //displayAll  应该是冻结首列的意思
                    if (displayAll) {
                        HSSFCell cell = rowBatch.createCell(Integer.valueOf(columnConfig.orderBy()));
                        cell.setCellStyle(cellStyle);
                        //判断是否已经获取过该code的字典数据 避免重复获取
                        cell.setCellValue(field.get(o) == null ? "" : field.get(o).toString());
                        cell.setCellType(CellType.STRING);
                    }
                    if (!displayAll && columnConfig.display()) {
                        HSSFCell cell = rowBatch.createCell(Integer.valueOf(columnConfig.orderBy()) - 1);
                        cell.setCellStyle(cellStyle);
                        cell.setCellValue(field.get(o) == null ? "" : field.get(o).toString());
                        cell.setCellType(CellType.STRING);
                    }
                }
            }

        }
        return  workbook;

    }



    public <T> void makeExcelByXlsTemplate(T t,String tplName,OutputStream os)throws  Exception{
        InputStream inputStream = new  ClasspathResourceLoader().getResourceStream(tplName);
        Map<String,String> fieldMap = new HashMap<>(64);
        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheet = workbook.getSheetAt(0);



        String value = "";
        List<Field> fileList = getMappedFiled(t.getClass(),null);
        for(Field field:fileList) {
            field.setAccessible(true);
            Type type = field.getType();
            if (type == String.class) {
                value = field.get(t)!=null?field.get(t).toString():"";
            } else if (type == Date.class) {
                value = format.format(field.get(t));
            } else if (type == Boolean.class) {
                value = field.get(t)!=null?field.get(t).toString():"";
            } else {
                value = field.get(t)!=null?field.get(t).toString():"";
            }
            ExcelDesc desc = field.getAnnotation(ExcelDesc.class);
            if (!"".equals(desc.value())) {
                fieldMap.put(desc.value(), value);
            }
        }

        int rowNum=sheet.getLastRowNum() + 1;
        int coloumNum=sheet.getRow(0).getPhysicalNumberOfCells();
        if(coloumNum>0 && rowNum>0){
            for(int i=0;i<rowNum;i++){
                 coloumNum=sheet.getRow(i).getPhysicalNumberOfCells();
                for(int j =0;j<coloumNum;j++){
                    Cell cell = sheet.getRow(i).getCell(j);
                    if(cell != null){
                        cell.setCellType(CellType.STRING);
                        System.out.println(cell.getStringCellValue());
                        String key = cell.getStringCellValue();
                        String cellValue = fieldMap.get(key);
                        if(cellValue != null ){
                            cell.setCellValue(cellValue);
                        }
                    }

                }
            }
        }
       workbook.write(os);
    }

    public static void main(String[] args) {
        try{
            OutputStream os = new FileOutputStream("E:\\temp\\temp.xls");
            ExcelTemplateModel model =new ExcelTemplateModel();
            model.setPortOfExport("出口口岸");
            model.setRecordNumber("备案号");
            model.setExportDate("出口日期");
            model.setManageUnit("经营单位");
            model.setTradeWay("运输方式");
            model.setTransportName("交通工具名称");
            model.setPackageNumber("提运单号");
            model.setDeclareDate("申报日期");
            ExcelUtils<ExcelTemplateModel> utils = new ExcelUtils<>(ExcelTemplateModel.class);
            utils.makeExcelByXlsTemplate(model,"vm" + File.separator + "excel.xls",os);
            os.flush();
            os.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}


