package com.ag.ui5.dialog;

import com.ag.ui5.types.FileType;
import com.ag.ui5.types.WarnType;
import com.ag.ui5.util.CreateFileContents;
import com.ag.ui5.util.FileGenerationHelper;
import com.intellij.icons.AllIcons;
import com.intellij.ide.actions.TemplateKindCombo;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.*;

public class NewFileDialog extends DialogWrapper {
    private Project project;
    private PsiDirectory psiDirectory;
    private JPanel contentPanel;
    private JTextField fileName;
    private TemplateKindCombo fileTypeBox;

    public NewFileDialog(Project project, PsiDirectory psiDirectory) {
        super(project);
        this.project = project;
        this.psiDirectory = psiDirectory;
        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return contentPanel;
    }

    @Override
    protected void init() {
        fileTypeBox.addItem(FileType.VIEW.getType(), AllIcons.FileTypes.Xml, FileType.VIEW.getType());
        fileTypeBox.addItem(FileType.CONTROLLER.getType(), AllIcons.FileTypes.JavaScript, FileType.CONTROLLER.getType());
        fileTypeBox.addItem(FileType.JSON.getType(), AllIcons.FileTypes.Json, FileType.JSON.getType());
        fileTypeBox.addItem(FileType.FRAGMENT.getType(), AllIcons.FileTypes.Xml, FileType.FRAGMENT.getType());
        fileTypeBox.addItem(FileType.PROPERTIES.getType(), AllIcons.FileTypes.Properties, FileType.PROPERTIES.getType());
        super.init();
    }

    @Override
    protected void doOKAction() {
        final String _fileName = fileName.getText();
        final String _fileType = fileTypeBox.getSelectedName();
        addNewFile(_fileName, _fileType);
        super.doOKAction();
    }

    private void addNewFile(String fileName, String fileType) {

        ProgressManager.getInstance().runProcessWithProgressSynchronously(new Runnable() {
            @Override
            public void run() {
                final VirtualFile root = psiDirectory.getVirtualFile();
                generateNewFile(root);
            }

            private void generateNewFile(VirtualFile virtualFile) {
                ApplicationManager.getApplication().invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        final String extension = FileGenerationHelper.getFileExtension(fileType);
                        final String target = FileGenerationHelper.getFileTarget(fileType);
                        final String suffix = FileGenerationHelper.getFileSuffix(fileType);
                        final Application application;
                        application = ApplicationManager.getApplication();
                        application.runWriteAction(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    VirtualFile folder = findFolder(virtualFile, target);
                                    String projectName = project.getName();
                                    String exactFileName = fileName + suffix + extension;
                                    String fileContent = "";

                                    if (fileType.equals(FileType.CONTROLLER.getType())) {
                                        fileContent = CreateFileContents.generateController(fileName, projectName);
                                    } else if (fileType.equals(FileType.VIEW.getType())) {
                                        fileContent = CreateFileContents.generateView(fileName, projectName);
                                    } else if(fileType.equals(FileType.FRAGMENT.getType())){
                                        fileContent = CreateFileContents.generateFragment(fileName, projectName);
                                    }

                                    VirtualFile generatedFile = folder.createChildData(null, exactFileName);
                                    generatedFile.setBinaryContent(fileContent.getBytes());

                                    //If it's an xml view generate controller too
                                    if(fileType.equals(FileType.VIEW.getType())){
                                        folder = findFolder(virtualFile, FileType.CONTROLLER.getTarget());
                                        fileContent = CreateFileContents.generateController(fileName, projectName);
                                        exactFileName = fileName + FileType.CONTROLLER.getSuffix() + FileType.CONTROLLER.getExtension();
                                        generatedFile = folder.createChildData(null, exactFileName);
                                        generatedFile.setBinaryContent(fileContent.getBytes());
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });
            }

            private VirtualFile findFolder(VirtualFile virtualFile, String target) {
                VirtualFile[] children = virtualFile.getChildren();
                VirtualFile parent = virtualFile.getParent();
                VirtualFile folder = null;
                if(parent != null && parent.getName().equals("webapp")){
                    folder = parent.findChild(target);
                }
                else if (children != null) {
                    for (VirtualFile f : children) {
                        String name = f.getName();
                        if (f.getName().equals(target)) {
                            folder = f;
                        } else if (f.getName().equals("webapp")) {
                            folder = f.findChild(target);
                        }
                    }
                } else if (children == null) {
                    for (VirtualFile f : parent.getParent().getChildren()) {
                        if (f.getName().equals(target)) {
                            folder = f;
                        }
                    }
                }
                return folder;
            }

        }, "", false, project);
    }

    @Nullable
    @Override
    protected ValidationInfo doValidate() {
        if (!StringUtils.isNotBlank(fileName.getText())) {
            return new ValidationInfo(WarnType.EMPTY.getWarn(), fileName);
        }
        return super.doValidate();
    }
}
