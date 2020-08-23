package org.fyan102.algorithms.demos;

import org.fyan102.algorithms.IStateRepresent;

import java.lang.reflect.Method;
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
    private int cost;
    private int heuristic;

    /**
     * The constructor of NPuzzle class
     *
     * @param number    the number of rows and columns
     * @param initTiles the initialized tiles should be represented as a String
     */
    public NPuzzle(int number, String initTiles) {
        this.number = number;
        String[] tileStrings = initTiles.split(" ");
        assert tileStrings.length == number * number;
        tiles = new String[number][];
        for (int i = 0; i < number; i++) { //i: y
            tiles[i] = new String[number];
            for (int j = 0; j < number; j++) {//j: x
                tiles[i][j] = tileStrings[i * number + j];
                if (tiles[i][j].equalsIgnoreCase("B")) {
                    blank = new Tile(j, i);
                }
            }
        }
        this.parent = null;
        assert blank != null;
        cost = 0;
    }

    public NPuzzle(String[][] tiles, NPuzzle parent) {
        this.tiles = tiles;
        this.parent = parent;
        this.number = tiles.length;
        for (int i = 0; i < number; i++) { //i: y
            for (int j = 0; j < number; j++) {//j: x
                if (tiles[i][j].equalsIgnoreCase("B")) {
                    blank = new Tile(j, i);
                }
            }
        }
        cost = parent.cost + (blank.x == 1 && blank.y == 1 ? 4 : 1);
    }

    /**
     * The constraint method
     *
     * @return true always.
     */
    @Override
    public boolean constraints() {
        return true;
    }

    /**
     * cost of each operation
     *
     * @return 1 always
     */
    @Override
    public double cost() {
        return cost;
    }

    /**
     * whether the current state is same as other state
     *
     * @param o the other state
     * @return true if the current state is same as other state, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NPuzzle nPuzzle = (NPuzzle) o;
        boolean eq = true;
        for (int i = 0; i < number && eq; i++) {
            for (int j = 0; j < number && eq; j++) {
                if (!tiles[i][j].equals(((NPuzzle) o).getTiles()[i][j]))
                    eq = false;
            }
        }
        return eq && getNumber() == nPuzzle.getNumber();
    }

    /**
     * accessor of blank
     *
     * @return blank
     */
    public Tile getBlank() {
        return blank;
    }

    /**
     * accessor of number
     *
     * @return number
     */
    public int getNumber() {
        return number;
    }

    /**
     * accessor of parent
     *
     * @return the parent
     */
    public NPuzzle getParent() {
        return parent;
    }

    /**
     * accessor of tiles
     *
     * @return tiles
     */
    public String[][] getTiles() {
        return tiles;
    }

    /**
     * the override hashCode method
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(getNumber());
        result = 31 * result + Arrays.hashCode(getTiles());
        return result;
    }

    /**
     * the heuristic
     *
     * @return the value of heuristic
     */
    @Override
    public double heuristics(IStateRepresent goal) {
        NPuzzle goalState = (NPuzzle) goal;
        // HEURISTIC START
        double result = 0;
        for (int i = 0; i < number; i++) {
            for (int j = 0; j < number; j++) {
                for (int k = 0; k < number; k++) {
                    for (int m = 0; m < number; m++) {
                        if (!tiles[i][j].equals("B")) {
                            if (tiles[i][j].equals(goalState.tiles[k][m])) {
                                result += Math.abs(i - k) + Math.abs(j - m);
                            }
                        }
                    }
                }
            }
        }
        heuristic = (int) result;
        return result;
        // HEURISTIC END
    }

    private String[][] copyTiles() {
        String[][] newTiles = new String[number][];
        for (int i = 0; i < number; i++) {
            newTiles[i] = new String[number];
            for (int j = 0; j < number; j++) {
                newTiles[i][j] = tiles[i][j];
            }
        }
        return newTiles;
    }

    /**
     * move down
     */
    public NPuzzle moveDown() {
        String[][] newTiles = copyTiles();
        newTiles[blank.y][blank.x] = newTiles[blank.y + 1][blank.x];
        newTiles[blank.y + 1][blank.x] = "B";
        return new NPuzzle(newTiles, this);
    }

    /**
     * move left
     */
    public NPuzzle moveLeft() {
        String[][] newTiles = copyTiles();
        newTiles[blank.y][blank.x] = newTiles[blank.y][blank.x - 1];
        newTiles[blank.y][blank.x - 1] = "B";
        return new NPuzzle(newTiles, this);
    }

    /**
     * move right
     */
    public NPuzzle moveRight() {
        String[][] newTiles = copyTiles();
        newTiles[blank.y][blank.x] = newTiles[blank.y][blank.x + 1];
        newTiles[blank.y][blank.x + 1] = "B";
        return new NPuzzle(newTiles, this);
    }

    /**
     * move up
     */
    public NPuzzle moveUp() {
        String[][] newTiles = copyTiles();
        newTiles[blank.y][blank.x] = newTiles[blank.y - 1][blank.x];
        newTiles[blank.y - 1][blank.x] = "B";
        return new NPuzzle(newTiles, this);
    }

    /**
     * return the possible operations
     *
     * @return a list of all possible operations
     */
    @Override
    public ArrayList<Method> operations() {
        ArrayList<Method> ops = new ArrayList<Method>();
        try {
            if (blank.x > 0) {
                ops.add(this.getClass().getMethod("moveLeft"));
            }
            if (blank.x < number - 1) {
                ops.add(this.getClass().getMethod("moveRight"));
            }
            if (blank.y > 0) {
                ops.add(this.getClass().getMethod("moveUp"));
            }
            if (blank.y < number - 1) {
                ops.add(this.getClass().getMethod("moveDown"));
            }
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return ops;
    }

    /**
     * mutator of blank
     *
     * @param blank the new blank
     */
    public void setBlank(Tile blank) {
        this.blank = blank;
    }

    /**
     * mutator of number
     *
     * @param number the new number
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * mutator of parent
     *
     * @param parent the new parent
     */
    public void setParent(NPuzzle parent) {
        this.parent = parent;
    }

    /**
     * mutator of tiles
     *
     * @param tiles the new tiles
     */
    public void setTiles(String[][] tiles) {
        this.tiles = tiles;
    }

    /**
     * the toString method
     *
     * @return a string
     */
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer("");
        for (int i = 0; i < number; i++) {
            for (int j = 0; j < number; j++) {
                buffer.append(tiles[i][j] + " ");
            }
            buffer.append("\n");
        }
        return buffer.toString() + "g = " + cost + ", h= " + heuristic+"\n";
    }

    /**
     * the Tile class represent the position of a tile
     */
    private class Tile {
        private int x;
        private int y;

        /**
         * constructor
         *
         * @param x the x value
         * @param y the y value
         */
        public Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
