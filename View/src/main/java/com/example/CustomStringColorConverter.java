package com.example;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.StringConverter;

public class CustomStringColorConverter extends StringConverter<Paint> {


    public CustomStringColorConverter() {
    }

    @Override
    public String toString(Paint color) {
        return color.toString();
    }

    @Override
    public Paint fromString(String s) {
        return Color.web(s);
    }
        /*
    public Color toColor(boolean state) {
        return state ? Color.GREEN : Color.RED;
    }

    public boolean fromColor(Color color) {
        return color.equals(Color.GREEN);
    }
         */
}

