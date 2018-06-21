package dubbo.wk.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Decription  文件压缩，解压缩工具类
 * Created by wgp on 2018/6/20.
 */
public class ZipFileUtils {
    public static void main(String[] args) throws Exception{
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath().toString() + File.separator + "temp";
        System.out.println(path);
        List<String> files = new ZipFileUtils().getFiles(path);

        ZipOutputStream  zipOut = new ZipOutputStream(new FileOutputStream(path + File.separator + System.currentTimeMillis() + "temp.zip"));

        for (String filename:files) {
            InputStream in = new FileInputStream(filename);
            ZipEntry ze = new ZipEntry(filename.substring(filename.lastIndexOf(File.separator) + 1));
            zipOut.putNextEntry(ze);
            int temp = 0;
            while ((temp=in.read()) != -1){
                zipOut.write(temp);
            }
            in.close();
        }
        zipOut.close();

    }

    //根据文件路径，获取所有的文件
    public List<String> getFiles(String path){
        List<String> list = new ArrayList<>();
        File temp = new File(path);
        if(temp.isFile() || !temp.exists()){
            System.out.println("临时文件目录不存在！");
        }else{
            File[] files = temp.listFiles();
            for (File file:files) {
                if(file.isFile()){
                    System.out.println(file.getAbsolutePath());
                    list.add(file.getAbsolutePath());
                }else{
                    list.addAll(getFiles(file.getAbsolutePath()));
                }
            }
        }

        return list;
    }


    /**
     * 根據文件的路徑 生成壓縮包
     * @param files
     * @param file
     */
    public static void zipFile(List<String> files,File file ){
        try{
            ZipOutputStream  zipOut = new ZipOutputStream(new FileOutputStream(file));
            for (String filename:files) {
                InputStream in = new FileInputStream(filename);
                ZipEntry ze = new ZipEntry(filename.substring(filename.lastIndexOf(File.separator) + 1));
                zipOut.putNextEntry(ze);
                int temp = 0;
                while ((temp=in.read()) != -1){
                    zipOut.write(temp);
                }
                in.close();
            }
            zipOut.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 根據字節生成壓縮包
     * @param bytes
     * @param file
     * @param fileName
     */
    public static void zipFile(byte[] bytes,File file,String fileName){
        try{
            ZipOutputStream  zipOut = new ZipOutputStream(new FileOutputStream(file));
            ZipEntry ze = new ZipEntry(fileName);
            zipOut.putNextEntry(ze);
            zipOut.write(bytes);
            zipOut.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
