package org.example;

import java.util.ArrayList;
import java.util.List;

public class GameOfLifeCell {
    private boolean alive;
    private boolean nextState;
    private ArrayList<GameOfLifeCell> listOfNeighbors = new ArrayList<GameOfLifeCell>();

    public GameOfLifeCell(boolean alive) {
        this.alive = alive;
    }

    public void addNeighbor(GameOfLifeCell neighbor) {
        listOfNeighbors.add(neighbor);
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean nextState() {
        int aliveNeighbors = 0;
        for (int i = 0; i < listOfNeighbors.size(); i++) {
            if (listOfNeighbors.get(i).isAlive()) {
                aliveNeighbors++;
            }
        }
        if (alive) {
            switch (aliveNeighbors) {
                case 2, 3:
                    nextState = true;
                    break;
                default:
                    nextState = false;
                    break;
            }
        } else if (aliveNeighbors == 3) {
            nextState = true;
        }

        return nextState;
    }

    public void updateState() {
        this.alive = nextState;
    }

    public void setCell(boolean value) {
        this.alive = value;
    }

}