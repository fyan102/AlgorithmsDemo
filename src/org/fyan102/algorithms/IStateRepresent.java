package org.fyan102.algorithms;

import java.util.ArrayList;

public interface IStateRepresent {
    /**
     * The operations() method should return a list of possible operations that is applicable to the current state.
     *
     * @return a list of possible operations.
     */
    public ArrayList operations();

    /**
     * The constraints() function should represent the constraint of the problem.
     *
     * @return true if the current state is valid, false otherwise
     */
    public boolean constraints();

    /**
     * The heuristic() function should be a heuristic
     *
     * @return a double value
     */
    public double heuristics();

    /**
     * The cost() function represent the cost of the current state
     *
     * @return the total cost of the current state.
     */
    public double cost();
}
