package com.example;



public enum FillOption {
    Low("Low", 10), //10
    Medium("Medium", 30),  //30
    High("High", 50) //50
    ;
    private final String label;
    private final int value;

    // Konstruktor do inicjowania wartości
    FillOption(String label, int value) {
        this.label = label;
        this.value = value;
    }

    public String toString() {
        return this.label + " (" + this.value + "%)";
    }

    public int getValue() {
        return value;
    }
}
