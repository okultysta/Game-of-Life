package org.example;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.Objects;


public class GameOfLifeCell {
    private boolean alive;
    private boolean nextState;
    private ArrayList<Boolean> listOfNeighbors;

    public GameOfLifeCell(boolean alive) {
        this.alive = alive;
        listOfNeighbors = new ArrayList<Boolean>();
    }

    public void addNeighbor(GameOfLifeCell neighbor) {
        listOfNeighbors.add(neighbor.isAlive());
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean nextState() {
        int aliveNeighbors = 0;
        for (int i = 0; i < listOfNeighbors.size(); i++) {
            if (listOfNeighbors.get(i)) {
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
        } else {
            nextState = false;
        }

        return nextState;
    }

    public void updateState() {
        this.alive = nextState;
    }

    public void setCell(boolean value) {
        this.alive = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof GameOfLifeCell that)) return false;

        return new EqualsBuilder().append(alive, that.alive).append(nextState, that.nextState).append(listOfNeighbors, that.listOfNeighbors).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(alive).append(nextState).append(listOfNeighbors).toHashCode();
    }

    @Override
    public String toString() {
        return "GameOfLifeCell{" +
                "alive=" + alive +
                ", nextState=" + nextState +
                ", listOfNeighbors=" + listOfNeighbors +
                '}';
    }
}