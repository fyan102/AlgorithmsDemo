package org.fyan102.algorithms.demo;

import org.fyan102.algorithms.interfaces.IAction;
import org.fyan102.algorithms.interfaces.IStateRepresent;

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
    private NPuzzle goalState;
    private int blankX;
    private int blankY;
    private String[][] tiles;
    private int cost;
    private double heuristic;
    
    /**
     * The constructor of NPuzzle class
     *
     * @param number    the number of rows and columns
     * @param initTiles the initialized tiles should be represented as a String
     */
    public NPuzzle(Integer number, String initTiles, IStateRepresent goal) {
        blankX = -1;
        blankY = -1;
        this.number = number;
        String[] tileStrings = initTiles.split(" ");
        assert tileStrings.length == number * number;
        tiles = new String[number][];
        for (int i = 0; i < number; i++) { //i: y
            tiles[i] = new String[number];
            for (int j = 0; j < number; j++) {//j: x
                tiles[i][j] = tileStrings[i * number + j];
                if (tiles[i][j].equalsIgnoreCase("B")) {
                    blankX = j;
                    blankY = i;
                }
            }
        }
        this.parent = null;
        assert blankX != -1;
        cost = 0;
        this.goalState = (NPuzzle) goal;
        if (this.goalState != null)
            heuristic = heuristic();
    }

    private NPuzzle(String[][] tiles, NPuzzle parent) {
        this.tiles = tiles;
        this.parent = parent;
        this.number = tiles.length;
        for (int i = 0; i < number; i++) { //i: y
            for (int j = 0; j < number; j++) {//j: x
                if (tiles[i][j].equalsIgnoreCase("B")) {
                    blankX = j;
                    blankY = i;
                }
            }
        }
        goalState = parent.goalState;
        cost = parent.cost + (blankX == 1 && blankY == 1 ? 4 : 1);
        heuristic = heuristic();
    }

    /**
     * whether the current state is same as other state
     *
     * @param o the other state
     * @return true if the current state is same as other state, false otherwise
     */
    @Override
    public boolean equals(IStateRepresent o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NPuzzle nPuzzle = (NPuzzle) o;
        boolean eq = true;
        for (int i = 0; i < number && eq; i++) {
            for (int j = 0; j < number; j++) {
                if (!tiles[i][j].equals(((NPuzzle) o).getTiles()[i][j])) {
                    eq = false;
                    break;
                }
            }
        }
        return eq && getNumber() == nPuzzle.getNumber();
    }
    
    public IStateRepresent getParent() {
        return parent;
    }

//    /**
//     * The constraint method
//     *
//     * @return true always.
//     */
//    public boolean constraints() {
//        return true;
//    }
    
    private String[][] copyTiles() {
        String[][] newTiles = new String[number][];
        for (int i = 0; i < number; i++) {
            newTiles[i] = new String[number];
            System.arraycopy(tiles[i], 0, newTiles[i], 0, number);
        }
        return newTiles;
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
     * the heuristic
     *
     * @return the value of heuristic
     */
    @Override
    public double heuristic() {
        // HEURISTIC START
        int heuristic = 0;
        for (int i = 0; i < number; i++) {
            for (int j = 0; j < number; j++) {
                for (int k = 0; k < number; k++) {
                    for (int m = 0; m < number; m++) {
                        if (!tiles[i][j].equals("B")) {
                            if (tiles[i][j].equals(goalState.tiles[k][m])) {
                                heuristic += Math.abs(i - k) + Math.abs(j - m);
                            }
                        }
                    }
                }
            }
        }
        // HEURISTIC END
        this.heuristic = heuristic;
        return heuristic;

    }

    /**
     * accessor of number
     *
     * @return number
     */
    private int getNumber() {
        return number;
    }

    /**
     * accessor of tiles
     *
     * @return tiles
     */
    private String[][] getTiles() {
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

    public boolean isGoalState() {
        return goalState.equals(this);
    }

    /**
     * move down
     */
    private NPuzzle moveDown() {
        String[][] newTiles = copyTiles();
        newTiles[blankY][blankX] = newTiles[blankY + 1][blankX];
        newTiles[blankY + 1][blankX] = "B";
        return new NPuzzle(newTiles, this);
    }

    /**
     * move left
     */
    private NPuzzle moveLeft() {
        String[][] newTiles = copyTiles();
        newTiles[blankY][blankX] = newTiles[blankY][blankX - 1];
        newTiles[blankY][blankX - 1] = "B";
        return new NPuzzle(newTiles, this);
    }

    /**
     * move right
     */
    private NPuzzle moveRight() {
        String[][] newTiles = copyTiles();
        newTiles[blankY][blankX] = newTiles[blankY][blankX + 1];
        newTiles[blankY][blankX + 1] = "B";
        return new NPuzzle(newTiles, this);
    }

    /**
     * move up
     */
    private NPuzzle moveUp() {
        String[][] newTiles = copyTiles();
        newTiles[blankY][blankX] = newTiles[blankY - 1][blankX];
        newTiles[blankY - 1][blankX] = "B";
        return new NPuzzle(newTiles, this);
    }

    /**
     * return the possible operations
     *
     * @return a list of all possible operations
     */
    @Override
    public ArrayList<IAction> operations() {
        ArrayList<IAction> ops = new ArrayList<>();
        if (blankX > 0) {
            ops.add(this::moveLeft);
        }
        if (blankX < number - 1) {
            ops.add(this::moveRight);
        }
        if (blankY > 0) {
            ops.add(this::moveUp);
        }
        if (blankY < number - 1) {
            ops.add(this::moveDown);
        }

        return ops;
    }

    /**
     * the toString method
     *
     * @return a string
     */
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < number; i++) {

            for (int j = 0; j < number; j++) {
                buffer.append(tiles[i][j]).append(" ");
            }
            buffer.append("\n");
        }
        return buffer.toString() + "g = " + cost +
                ", h = " + heuristic +
                ", f = " + (cost + heuristic) + "\n";
    }
}
