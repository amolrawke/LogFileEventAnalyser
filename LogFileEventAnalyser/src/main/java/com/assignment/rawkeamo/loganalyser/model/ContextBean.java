package com.assignment.rawkeamo.loganalyser.model;

public class ContextBean {
    private static ContextBean INSTANCE;

    private String logFilePath;

    private ContextBean() {
    }

    public static ContextBean getInstance() {
        if (INSTANCE == null) INSTANCE = new ContextBean();
        return INSTANCE;
    }

    public String getLogFilePath() {
        return logFilePath;
    }

    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }

}
