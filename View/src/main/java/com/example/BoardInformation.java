package com.example;

public class BoardInformation {
    private int row;
    private int col;
    private int fillPercentage;

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getFillPercentage() {
        return fillPercentage;
    }

    public void setFillPercentage(int fillPercentage) {
        this.fillPercentage = fillPercentage;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }
}
