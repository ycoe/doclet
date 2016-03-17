package com.duoec.docs.helper;

import com.duoec.doclet.constant.DocletConstant;
import com.duoec.docs.logger.Logger;

import java.io.*;

/**
 * Created by ycoe on 15/12/2.
 */
public class FileHelper {
    private static final Logger logger = Logger.getInstance(FileHelper.class);

    public static void mkdir(String dirPath){
        File dir = new File(dirPath);
        mkdir(dir);
    }

    public static void mkdir(File dir){
        if(!(dir.exists() && dir.isDirectory())){
            File parent = dir.getParentFile();
            mkdir(parent);
            dir.mkdir();
            logger.info("创建目录: {}", dir.getAbsolutePath());
        }
    }

    public static void write(File dir, String fileName, String content){
        mkdir(dir);
        write(dir.getAbsoluteFile() + "/" + fileName, content);
    }

    private static void write(String filePath, String content){
        FileWriter fw = null;
        try {
            fw = new FileWriter(filePath);
            fw.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String read(File file) {
        StringBuilder sb = new StringBuilder();
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader dr = null;
        try {
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            dr = new BufferedReader(isr);
            String line = null;
            while((line = dr.readLine()) != null){
                sb.append(line);
                sb.append(DocletConstant.TURN_LINE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(dr != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(isr != null){
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    public static String formatFilePath(File java){
        String path = java.getParentFile().getPath();
//        String customerPackagePath = SpringMvcDocletConstant.CUSTOMER_PACKAGE.replaceAll("\\.", "/") + "/";
//        int index = path.indexOf(customerPackagePath);
////        int index = path.indexOf("com/fangdd/");
//        if(index != -1){
//            path = path.substring(index).replace("/", "_");
//            path = path.replace(".java", "");
//        }
        String fileName = java.getName();
//        fileName = fileName.replace(".java", ".md");
        return path + "_" + fileName;
    }

    /**
     *
     * @param java
     * @param type 1:summary 2:api
     * @return
     */
    public static String getJavaSrc(File java, int type) {
        String dir = "../src/";
        if(type == 1){
            dir = "src/";
        }
        StringBuilder sb = new StringBuilder();
        if(type == 1){
            sb.append(DocletConstant.TAB);
        }
        sb.append("* [");
        sb.append(java.getName());
        sb.append("](");
        sb.append(dir + formatFilePath(java));
        sb.append(")");
        sb.append(DocletConstant.TURN_LINE);
        return sb.toString();
    }
}
