package com.ag.ui5.util;

import sun.misc.IOUtils;
import sun.nio.ch.IOUtil;
import java.io.*;
import java.util.stream.Collectors;

public class CreateFileContents {
    private static final String controllerTempFileName = "Controller.js";
    private static final String viewTempFileName = "View.xml";
    private static final String fragmentTempFileName = "Fragment.xml";
    private static final String gruntFileName = "Gruntfile.js";
    private static final String packageJsonFileName = "package.json";

    public static String generateFile(String fileName, String projectName){
        String content = getContentFromTemplateFile(fileName);
        content = content.replaceAll("ROOT", projectName);
        return content;
    }

    public static String generateController(String controllerName, String projectName){
        String content = getContentFromTemplateFile(controllerTempFileName);
        content = content.replace("ROOT", projectName);
        content = content.replace("CONTROLLER_NAME", controllerName);
        return content;
    }

    public static String generateView(String viewName, String projectName){
        String content = getContentFromTemplateFile(viewTempFileName);
        content = content.replace("ROOT", projectName);
        content = content.replace("VIEW_NAME", viewName);
        return content;
    }

    public static String generateFragment(String fragmentName, String projectName){
        return getContentFromTemplateFile(fragmentTempFileName);
    }

    public static String generateGruntfile(String projectName){
        return getContentFromTemplateFile(gruntFileName);
    }

    public static String generatePackageJson(String projectName){
        String content = getContentFromTemplateFile(packageJsonFileName);
        content = content.replace("NAME", projectName);
        return content;
    }

    private static String getContentFromTemplateFile(String fileName){
        String filePath = "/Templates/" + fileName;
        InputStreamReader inputStreamReader = new InputStreamReader(CreateFileContents.class.getResourceAsStream(filePath));
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String content = bufferedReader.lines().collect(Collectors.joining(
                System.getProperty("line.separator")));
        return content;
    }
}
