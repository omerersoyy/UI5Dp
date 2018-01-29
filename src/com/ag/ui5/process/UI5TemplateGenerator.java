package com.ag.ui5.process;

import com.ag.ui5.util.CreateFileContents;
import com.ag.ui5.util.MoveUI5Resources;
import com.intellij.ide.util.projectWizard.WebProjectTemplate;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.logging.FileHandler;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UI5TemplateGenerator extends WebProjectTemplate {
    final String WEBAPP_DIR = "webapp";
    final String CONTROLLER = "controller";
    final String VIEW = "view";
    final String FRAGMENT = "fragment";
    final String CSS = "css";
    final String MODEL = "model";
    final String I18N = "i18n";
    final String COMPONENT_JS = "Component.js";
    final String INDEX_HTML = "index.html";
    final String MANIFEST_JSON = "manifest.json";
    final String I18N_PROP = "i18n.properties";
    final String GRUNTFILEJS = "Gruntfile.js";
    final String PACKAGEJSON = "package.json";

    @Override
    public String getDescription() {
        return "Generate an SAP/Open UI5 Application";
    }

    @Nls
    @NotNull
    @Override
    public String getName() {
        return "UI5 Application";
    }

    @Override
    public void generateProject(@NotNull Project project, @NotNull VirtualFile virtualFile, @NotNull Object object, @NotNull Module module) {
        ProgressManager.getInstance().runProcessWithProgressSynchronously(new Runnable() {
            @Override
            public void run() {
                try {
                    generateFoldersAndFiles(virtualFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            private void generateFoldersAndFiles(VirtualFile virtualFile) throws IOException {
                String projectName = project.getName();
                ApplicationManager.getApplication().invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        final Application application;
                        application = ApplicationManager.getApplication();
                        application.runWriteAction(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    VirtualFile webapp = virtualFile.createChildDirectory(null, WEBAPP_DIR);
                                    VirtualFile controller = webapp.createChildDirectory(null, CONTROLLER);
                                    VirtualFile view = webapp.createChildDirectory(null, VIEW);
                                    VirtualFile fragment = webapp.createChildDirectory(null, FRAGMENT);
                                    VirtualFile css = webapp.createChildDirectory(null, CSS);
                                    VirtualFile model = webapp.createChildDirectory(null, MODEL);
                                    VirtualFile manifest = webapp.createChildData(null, MANIFEST_JSON);
                                    VirtualFile component = webapp.createChildData(null, COMPONENT_JS);
                                    VirtualFile i18n = webapp.createChildDirectory(null, I18N);
                                    VirtualFile indexHtml = webapp.createChildData(null, INDEX_HTML);
                                    VirtualFile gruntFile = virtualFile.createChildData(null, GRUNTFILEJS);
                                    VirtualFile packageJson = virtualFile.createChildData(null, PACKAGEJSON);

                                    VirtualFile mainController = controller.createChildData(null, "Main.controller.js");
                                    VirtualFile mainView = view.createChildData(null, "Main.view.xml");
                                    VirtualFile i18nProp = i18n.createChildData(null, I18N_PROP);
                                    VirtualFile modelsJs = model.createChildData(null, "models.js");

                                    String indexHtmlContent = CreateFileContents.generateFile("index.html", projectName);
                                    String componentJsContent = CreateFileContents.generateFile("Component.js", projectName);
                                    String manifestJsonContent = CreateFileContents.generateFile("manifest.json", projectName);
                                    String mainControllerContent = CreateFileContents.generateController("Main", projectName);
                                    String mainViewContent = CreateFileContents.generateView("Main", projectName);
                                    String modelsJsContent = CreateFileContents.generateFile("models.js", projectName);
                                    String gruntFileContent = CreateFileContents.generateGruntfile(projectName);
                                    String packageJsonContent = CreateFileContents.generatePackageJson(projectName);

                                    indexHtml.setBinaryContent(indexHtmlContent.getBytes());
                                    component.setBinaryContent(componentJsContent.getBytes());
                                    manifest.setBinaryContent(manifestJsonContent.getBytes());
                                    mainController.setBinaryContent(mainControllerContent.getBytes());
                                    mainView.setBinaryContent(mainViewContent.getBytes());
                                    modelsJs.setBinaryContent(modelsJsContent.getBytes());
                                    gruntFile.setBinaryContent(gruntFileContent.getBytes());
                                    packageJson.setBinaryContent(packageJsonContent.getBytes());

                                    //MoveUI5Resources.unzip(UI5TemplateGenerator.class.getResourceAsStream("/ui5resources.zip"), webapp.getPath() + File.separator + "ui5resources");

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });

            }

        }, "", false, project);
    }
}
