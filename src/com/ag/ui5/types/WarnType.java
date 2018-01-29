package com.ag.ui5.types;

public enum WarnType {
    EMPTY ("This field can not be empty!"),
    NOTFOUND ("Not Found!"),
    WRONGTYPE ("Open file is not a controller or a view!"),
    NOASSOCIATEDFILE ("Associated view-controller not found!");

    private String warn;
    WarnType(String warn){
        this.warn = warn;
    }

    public String getWarn() {
        return warn;
    }
}
