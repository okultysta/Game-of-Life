package com.example;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.StringConverter;

public class CustomStringColorConverter extends StringConverter<Paint> {


    public CustomStringColorConverter() {
    }

    @Override
    public String toString(Paint color) {
        if (color == Color.GREEN) {
            return "Green";
        } else if (color == Color.RED) {
            return "Red";
        }
        return "Unknown";
    }

    @Override
    public Paint fromString(String s) {
        if (s.equals("Green")) {
            return Color.GREEN;
        } else if (s.equals("Red")) {
            return Color.RED;
        } else {
            return Color.WHITE;
        }
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

