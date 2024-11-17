package org.example;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;


public class GameOfLifeColumnRow {
    private ArrayList<GameOfLifeCell> cells;

    public GameOfLifeColumnRow() {
        cells = new ArrayList<GameOfLifeCell>();
    }

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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("cells", cells)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof GameOfLifeColumnRow that)) {
            return false;
        }

        return new EqualsBuilder().append(cells, that.cells).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(cells).toHashCode();
    }


}

