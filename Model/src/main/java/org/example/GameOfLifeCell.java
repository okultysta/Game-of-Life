package org.example;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.example.exceptions.NothingToCompareException;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;


public class GameOfLifeCell implements Serializable, Comparable<GameOfLifeCell>, Cloneable {
    @Serial
    private static final long serialVersionUID = 6501629135049586390L;
    private boolean alive;
    private transient boolean nextState;
    private ArrayList<GameOfLifeCell> listOfNeighbors;

    public GameOfLifeCell(boolean alive) {
        this.alive = alive;
        listOfNeighbors = new ArrayList<GameOfLifeCell>();
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
        if (this == o) {
            return true;
        }

        if (!(o instanceof GameOfLifeCell that)) {
            return false;
        }

        return new EqualsBuilder().append(alive, that.alive).append(nextState, that.nextState).isEquals();
    }

    @Override
    public int hashCode() {
        ArrayList<Boolean> local = new ArrayList<>();
        for (GameOfLifeCell cell : listOfNeighbors) {
            local.add(cell.isAlive());
        }
        return new HashCodeBuilder(17, 37).append(alive).append(nextState).append(local).toHashCode();
    }

    @Override
    public String toString() {
        //ArrayList<Boolean> local = new ArrayList<>();
        /*
        for (GameOfLifeCell cell : listOfNeighbors) {
            local.add(cell.isAlive());
        }
        return new ToStringBuilder(this).append("alive: ", alive).append("nextState: ", nextState)
                .append("listOfNeighbors: ", listOfNeighbors).toString();
                */
        return new ToStringBuilder(this).append("alive: ", alive).append("nextState: ", nextState).toString();
    }

    @Override
    public int compareTo(GameOfLifeCell o) {
        if (o == null) {

            throw new NothingToCompareException("objectIsNull",null);

            }
        if (this.alive != o.alive) {
            return Boolean.compare(this.alive, o.alive);
        }
        if (this.nextState != o.nextState) {
            return Boolean.compare(this.nextState, o.nextState);
        }
        return 0;
    }

    @Override
    public GameOfLifeCell clone() {
        try {
            GameOfLifeCell clone = (GameOfLifeCell) super.clone();
            for (GameOfLifeCell cell : this.listOfNeighbors) {
                clone.listOfNeighbors.add(cell.clone());
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
