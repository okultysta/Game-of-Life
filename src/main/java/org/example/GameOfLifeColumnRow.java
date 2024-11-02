package org.example;

import java.util.ArrayList;

public class GameOfLifeColumnRow {
    private ArrayList<GameOfLifeCell> cells = new ArrayList<GameOfLifeCell>();

    public void addCell(GameOfLifeCell cell) {
        cells.add(cell);
    }

    public int countAlive() {
        int count = 0;
        for (GameOfLifeCell cell : cells) {
            if (cell.isAlive()) {
                count++;
            }
        }
        return count;
    }

    public int countDead() {
        int count = 0;
        for (GameOfLifeCell cell : cells) {
            if (!cell.isAlive()) {
                count++;
            }
        }
        return count;
    }
}
