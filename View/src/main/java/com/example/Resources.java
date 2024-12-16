package com.example;

import java.util.ListResourceBundle;

public class Resources extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                {"Author1", "Kacper Maziarz"},
                {"Author2", "Jedrzej Bartoszewski"},
        };
    }
}
