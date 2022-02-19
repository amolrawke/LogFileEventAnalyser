package com.assignment.rawkeamo.loganalyser.model;

import java.util.Arrays;

public enum TypeOfEvents {
    APPLICATION_LOG("APPLICATION_LOG");

    private final String value;

    TypeOfEvents(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TypeOfEvents fromValue(String text) {
        return Arrays.stream(values())
                .filter(v -> v.getValue().equals(text))
                .findFirst()
                .orElse(null);
    }
}
