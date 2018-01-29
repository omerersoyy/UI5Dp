package com.ag.ui5.types;

public enum FileType {
    VIEW("View", ".view", ".xml", "view"),
    CONTROLLER("Controller", ".controller", ".js", "controller"),
    FRAGMENT("Fragment", ".fragment", ".xml", "fragment"),
    JSON("Json", "", ".json", "webapp"),
    PROPERTIES("Properties", "", ".properties", "i18n");

    private String type;
    private String extension;
    private String target;
    private String suffix;

    FileType(String type, String suffix, String extension, String target) {
        this.type = type;
        this.target = target;
        this.extension = extension;
        this.suffix = suffix;
    }

    public String getType() {
        return type;
    }
    public String getExtension() {
        return extension;
    }
    public String getTarget() {
        return target;
    }
    public String getSuffix(){ return suffix; }
}
