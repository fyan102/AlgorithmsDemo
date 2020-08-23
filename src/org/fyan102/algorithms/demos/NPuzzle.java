package org.fyan102.algorithms.demos;

import org.fyan102.algorithms.IStateRepresent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * The NPuzzle class represents a NPuzzle, and the actions such as moving left ( move the blank cell
 * to its left cell), moving right, moving up and moving down are implemented in this class. There
 * are also methods that can be used to compare whether two puzzles are the same, and the method that
 * convert the puzzle to a readable format is also provided
 *
 * @author Fan
 * @version 0.0
 */
public class NPuzzle implements IStateRepresent {
    private int number;
    private NPuzzle parent;
    private Tile blank;
    private String[][] tiles;
    public NPuzzle(int number, String initTiles, NPuzzle parent) {
        this.number = number;
        String[] tileStrings = initTiles.split(" ");
        assert tileStrings.length == number * number;
        tiles = new String[number][];
        for (int i = 0; i < number; i++) {
            tiles[i] = new String[number];
            for (int j = 0; j < number; j++) {
                tiles[i][j] = tileStrings[i * number + j];
                if (tiles[i][j].equalsIgnoreCase("B")) {
                    blank = new Tile(i, j);
                }
            }
        }
        this.parent = parent;
        assert blank != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NPuzzle nPuzzle = (NPuzzle) o;
        return getNumber() == nPuzzle.getNumber() &&
                Arrays.equals(getTiles(), nPuzzle.getTiles());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getNumber());
        result = 31 * result + Arrays.hashCode(getTiles());
        return result;
    }

    @Override
    public boolean constraints() {
        return false;
    }

    @Override
    public double cost() {
        return 0;
    }

    public Tile getBlank() {
        return blank;
    }

    public int getNumber() {
        return number;
    }

    public NPuzzle getParent() {
        return parent;
    }

    public String[][] getTiles() {
        return tiles;
    }

    @Override
    public double heuristics() {
        return 0;
    }

    @Override
    public ArrayList operations() {
        return null;
    }

    public void setBlank(Tile blank) {
        this.blank = blank;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setParent(NPuzzle parent) {
        this.parent = parent;
    }

    public void setTiles(String[][] tiles) {
        this.tiles = tiles;
    }

    private class Tile {
        private int x;
        private int y;

        public Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
