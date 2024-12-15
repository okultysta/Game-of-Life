package com.example;

import javafx.util.StringConverter;

public class CustomBooleanStringConverter extends StringConverter<Boolean> {
    @Override
    public String toString(Boolean aBoolean) {
        return aBoolean.toString();
    }

    @Override
    public Boolean fromString(String s) {
        return Boolean.parseBoolean(s);
    }
}
