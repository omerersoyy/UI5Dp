package com.ag.ui5.action;

import com.ag.ui5.types.WarnType;
import com.ag.ui5.util.KeyValuePairHelper;
import com.intellij.codeInsight.hint.HintManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import kotlin.Pair;

import java.io.*;

public class AddNamespaceAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = getEventProject(e);
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        PsiFile file = e.getData(PlatformDataKeys.PSI_FILE);
        Caret caret = e.getData(PlatformDataKeys.CARET);

        if (project == null || editor == null || file == null || caret == null) {
            return;
        }
        final String selectedText = caret.getSelectedText();
        boolean isController = file.getName().contains("controller");
        final Pair pair;
        String key = "";
        String value = "";

        try {
            pair = KeyValuePairHelper.getNamespace(selectedText);
            key = pair.getFirst().toString();
            value = pair.getSecond().toString();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        if (!value.equals("") && isController) {
            final StringBuilder namespaceValue = new StringBuilder();
            final StringBuilder namespaceKey = new StringBuilder();

            namespaceValue.insert(0, ",\n\t" + "\"" + value + "\"");
            namespaceKey.insert(0, ", " + key);
            Document document = editor.getDocument();

            CommandProcessor.getInstance().executeCommand(project, () -> ApplicationManager.getApplication().runWriteAction(new Runnable() {
                @Override
                public void run() {
                    int indexOfNamespace = document.getText().indexOf("]") - 1;
                    document.insertString(indexOfNamespace, namespaceValue.toString());
                    int indexOfFunction = document.getText().indexOf(")");
                    document.insertString(indexOfFunction, namespaceKey.toString());
                }
            }), "", null);
        } else {
            HintManager.getInstance().showErrorHint(editor, WarnType.NOTFOUND.getWarn());
        }
    }
}

