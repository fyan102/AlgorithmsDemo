package org.fyan102.algorithms;

import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * This interface is used for representing a state of a problem.
 *
 * @author Fan
 * @version 0.0
 */
public interface IStateRepresent {
    /**
     * Check whether the current state is the goal state
     *
     * @return true if the current state is the goal state, false otherwise
     */
    boolean isGoalState();

    /**
     * The operations() method should return a list of possible operations that is applicable to the current state.
     *
     * @return a list of possible operations.
     */
    ArrayList<Action> operations();

    /**
     * The constraints() function should represent the constraint of the problem.
     *
     * @return true if the current state is valid, false otherwise
     */
    boolean constraints();

    /**
     * The heuristic() function should be a heuristic
     *
     * @return a double value
     */
    double heuristics();

    /**
     * The cost() function represent the cost of the current state
     *
     * @return the total cost of the current state.
     */
    double cost();
}
