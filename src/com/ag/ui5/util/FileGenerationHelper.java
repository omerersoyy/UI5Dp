package com.ag.ui5.util;

import com.ag.ui5.types.FileType;

public class FileGenerationHelper {

    public static String getFileExtension(String fileType) {
        String extension;
        switch (fileType) {
            case "Controller":
                extension = FileType.CONTROLLER.getExtension();
                break;
            case "View":
                extension = FileType.VIEW.getExtension();
                break;
            case "Fragment":
                extension = FileType.FRAGMENT.getExtension();
                break;
            case "Json":
                extension = FileType.JSON.getExtension();
                break;
            case "Properties":
                extension = FileType.PROPERTIES.getExtension();
                break;
            default:
                extension = "";
        }
        return extension;
    }

    public static String getFileTarget(String fileType) {
        String target;
        switch (fileType) {
            case "Controller":
                target = FileType.CONTROLLER.getTarget();
                break;
            case "View":
                target = FileType.VIEW.getTarget();
                break;
            case "Fragment":
                target = FileType.FRAGMENT.getTarget();
                break;
            case "Json":
                target = FileType.JSON.getTarget();
                break;
            case "Properties":
                target = FileType.PROPERTIES.getTarget();
                break;
            default:
                target = "";
        }
        return target;
    }

    public static String getFileSuffix(String fileType) {
        String suffix;
        switch (fileType) {
            case "Controller":
                suffix = FileType.CONTROLLER.getSuffix();
                break;
            case "View":
                suffix = FileType.VIEW.getSuffix();
                break;
            case "Fragment":
                suffix = FileType.FRAGMENT.getSuffix();
                break;
            case "Json":
                suffix = FileType.JSON.getSuffix();
                break;
            case "Properties":
                suffix = FileType.PROPERTIES.getSuffix();
                break;
            default:
                suffix = "";
        }
        return suffix;
    }
}
