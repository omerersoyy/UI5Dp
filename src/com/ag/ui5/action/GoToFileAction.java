package com.ag.ui5.action;

import com.ag.ui5.types.FileType;
import com.ag.ui5.types.WarnType;
import com.intellij.codeInsight.hint.HintManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;

public class GoToFileAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        PsiFile file = e.getData(PlatformDataKeys.PSI_FILE);
        String openFileName = file.getName();
        String associatedFileName = "";
        String openFileType = "";
        String target = "";
        if (openFileName == null) {
            return;
        }
        ;

        if (openFileName.contains(FileType.CONTROLLER.getSuffix())) {
            openFileType = FileType.CONTROLLER.getType();
        } else if (openFileName.contains(FileType.VIEW.getSuffix())) {
            openFileType = FileType.VIEW.getType();
        } else {
            HintManager.getInstance().showErrorHint(editor, WarnType.WRONGTYPE.getWarn());
            return;
        }

        if (openFileType.equals(FileType.CONTROLLER.getType())) {
            associatedFileName = openFileName.replace("controller.js", "view.xml");
            target = FileType.VIEW.getTarget();
        } else if (openFileType.equals(FileType.VIEW.getType())) {
            associatedFileName = openFileName.replace("view.xml", "controller.js");
            target = FileType.CONTROLLER.getTarget();
        }

        if (associatedFileName == "") {
            HintManager.getInstance().showErrorHint(editor, WarnType.NOASSOCIATEDFILE.getWarn());
        } else {

            VirtualFile webapp = file.getVirtualFile().getParent().getParent();
            VirtualFile associatedFile = webapp.findChild(target).findChild(associatedFileName);

            if (webapp != null && associatedFile != null) {
                CommandProcessor.getInstance().executeCommand(project, () -> ApplicationManager.getApplication().runWriteAction(new Runnable() {
                    @Override
                    public void run() {
                        new OpenFileDescriptor(project, associatedFile).navigate(true);
                    }
                }), "", null);
            }
        }
    }
}
