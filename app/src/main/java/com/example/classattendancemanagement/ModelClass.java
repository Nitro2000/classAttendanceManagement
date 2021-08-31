package com.example.classattendancemanagement;

public class ModelClass {

    private String className;
    private String subjName;

    public ModelClass(String className, String subjName) {
        this.className = className;
        this.subjName = subjName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSubjName() {
        return subjName;
    }

    public void setSubjName(String subjName) {
        this.subjName = subjName;
    }
}
