package com.ag.ui5.action;

import com.ag.ui5.dialog.NewFileDialog;
import com.intellij.ide.IdeView;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;

public class NewFileAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        final DataContext dataContext = anActionEvent.getDataContext();
        final IdeView view = LangDataKeys.IDE_VIEW.getData(dataContext);

        if (view == null) {
            return;
        }

        final Project project = CommonDataKeys.PROJECT.getData(dataContext);

        final PsiDirectory psiDirectory = view.getOrChooseDirectory();
        if (psiDirectory == null || project == null) {
            return;
        }
        NewFileDialog newFileDialog = new NewFileDialog(project, psiDirectory);
        newFileDialog.show();
    }
}
