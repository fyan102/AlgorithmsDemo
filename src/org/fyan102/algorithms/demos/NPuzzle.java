package org.fyan102.algorithms.demos;

import org.fyan102.algorithms.IStateRepresent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * The NPuzzle class represents a NPuzzle, and the actions such as moving left ( move the blank cell
 * to its left cell), moving right, moving up and moving down are implemented in this class. There
 * are also methods that can be used to compare whether two puzzles are the same, and the method that
 * convert the puzzle to a readable format is also provided.
 *
 * @author Fan
 * @version 0.0
 */
public class NPuzzle implements IStateRepresent {
    private int number;
    private NPuzzle parent;
    private Tile blank;
    private String[][] tiles;

    /**
     * @param number
     * @param initTiles
     * @param parent
     */
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

    /**
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NPuzzle nPuzzle = (NPuzzle) o;
        return getNumber() == nPuzzle.getNumber() &&
                Arrays.equals(getTiles(), nPuzzle.getTiles());
    }

    /**
     * @return
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(getNumber());
        result = 31 * result + Arrays.hashCode(getTiles());
        return result;
    }

    /**
     * @return
     */
    @Override
    public boolean constraints() {
        return false;
    }

    /**
     * @return
     */
    @Override
    public double cost() {
        return 0;
    }

    /**
     * @return
     */
    public Tile getBlank() {
        return blank;
    }

    /**
     * @return
     */
    public int getNumber() {
        return number;
    }

    /**
     * @return
     */
    public NPuzzle getParent() {
        return parent;
    }

    /**
     * @return
     */
    public String[][] getTiles() {
        return tiles;
    }

    /**
     * the heuristic
     *
     * @return
     */
    @Override
    public double heuristics() {
        // TODO: to be replace
        return 0;
    }

    /**
     * @return
     */
    @Override
    public ArrayList operations() {
        return null;
    }

    /**
     * @param blank
     */
    public void setBlank(Tile blank) {
        this.blank = blank;
    }

    /**
     * @param number
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * @param parent
     */
    public void setParent(NPuzzle parent) {
        this.parent = parent;
    }

    /**
     * @param tiles
     */
    public void setTiles(String[][] tiles) {
        this.tiles = tiles;
    }

    /**
     *
     */
    private class Tile {
        private int x;
        private int y;

        /**
         * @param x
         * @param y
         */
        public Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
