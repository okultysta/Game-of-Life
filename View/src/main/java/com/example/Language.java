package com.example;

public enum Language {
    POLISH("Polski"),
    ENGLISH("English");


    private final String label;

    Language(String label) {
        this.label = label;
    }

    public String toString() {
        return this.label;
    }
}
