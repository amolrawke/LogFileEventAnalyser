package com.assignment.rawkeamo.loganalyser.model;

import java.util.Arrays;

public enum StateMgmBean {
    STARTED("STARTED"),
    FINISHED("FINISHED");

    private final String value;

    StateMgmBean(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static StateMgmBean fromValue(String text) {
        return Arrays.stream(values())
                .filter(v -> v.getValue().equals(text))
                .findFirst()
                .orElse(null);
    }
}
