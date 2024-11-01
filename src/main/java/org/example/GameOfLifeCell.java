package org.example;

public class GameOfLifeCell {
   private boolean alive;
    public GameOfLifeCell(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }
    public boolean nextState() {
        return false;
    }
    public void updateState(boolean value) {
        this.alive = value;
    }
}
