package com.ag.ui5.action;

import com.intellij.codeInsight.hint.HintManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/*
**
* Not implemented yet
**
*/
public class TestingWithGrunt extends AnAction{

    @java.lang.Override
    public void actionPerformed(AnActionEvent anActionEvent){
        Editor ed = anActionEvent.getData(PlatformDataKeys.EDITOR);
        String path = ed.getProject().getBasePath();
        File file = new File(path);
        try {
            //final Process p = Runtime.getRuntime().exec(new String[]{"cmd /c npm install", "cmd /c grunt serve"}, null, file);
            final Process p = Runtime.getRuntime().exec("cmd /c grunt serve", null, file);
            new Thread(new Runnable() {
                public void run() {
                    BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String line = null;

                    try {
                        while ((line = input.readLine()) != null)
                            System.out.println(line);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            p.waitFor();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
