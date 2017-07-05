package com.duoec.test.test;

import com.duoec.docs.SpringMVCDoclet;
import com.duoec.docs.helper.SystemHelper;

import java.io.File;

/**
 * Created by ycoe on 15/12/29.
 */
public class SpringMVCDocletTest {
    public static void main(String[] args){
        String projectPath = SystemHelper.getProjectPath();

        String projectSrcDirs = getProjectSrcDirs();
        System.out.println("projectPath:" + projectPath);
        String projectSrcPath = projectPath + "/MVCTest/src/main/java/";
        String[] docArgs = new String[]{
                "-doclet",
                SpringMVCDoclet.class.getName(),
                projectSrcPath + "com/duoec/test/controller/UserController.java",
                "-sourcepath",
                projectSrcDirs
        };
        com.sun.tools.javadoc.Main.execute(docArgs);
    }


    public static String getProjectSrcDirs(){
        String projectPath = SystemHelper.getProjectPath();

        StringBuilder srcPath = new StringBuilder();
        File projectDir = new File(projectPath);
        File[] fs = projectDir.listFiles();
        for(File f : fs){
            if(f.isFile())
                continue;

            File srcDir = new File(f.getAbsolutePath() + "/src/main/java");
            if(!srcDir.exists())
                continue;

            if(srcPath.length() > 0){
                srcPath.append(":");
            }
            srcPath.append(srcDir.getAbsolutePath());
        }
        return srcPath.toString();
    }
}
