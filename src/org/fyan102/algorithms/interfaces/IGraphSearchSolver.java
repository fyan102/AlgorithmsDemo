package org.fyan102.algorithms.interfaces;

import java.util.Collection;

/**
 * Any classes that implements the IGraphSearchSolver interface should be able
 * to reset the solver to an initial state, solve the problem, and solve the problem
 * step by step. It should also be able to return a search tree which shows the
 * procedures of the searching.
 *
 * @author Fan
 * @version 1.0
 */
public interface IGraphSearchSolver {
    /**
     * getSearchTree() should return a search tree which represents the procedures
     * of the searching
     *
     * @return the search tree.
     */
    ISearchTree<IStateRepresent> getSearchTree();
    
    /**
     * The reset method should be able to reset the solver to an initial state
     */
    void reset();
    
    Collection<IStateRepresent> getClosedSet();
    
    /**
     * The solveOneStep method takes the current state and solve the problem by one step
     *
     * @return the next state of the problem
     */
    IStateRepresent solveOneStep();
    
    Collection<IStateRepresent> getOpenSet();
    
    default boolean notExist(IStateRepresent newState) {
        boolean exist = false;
        for (IStateRepresent state : getClosedSet()) {
            if (state.equals(newState)) {
                exist = true;
                break;
            }
        }
        for (IStateRepresent state : getOpenSet()) {
            if (state.equals(newState)) {
                exist = true;
                break;
            }
        }
        return !exist;
    }
    
    void setInitState(IStateRepresent initState);
    
    /**
     * The solve method will take the initial state and solve the problem
     */
    default void solve() {
        reset();
        while (!getOpenSet().isEmpty()) {
            IStateRepresent state = solveOneStep();
            if (state.isGoalState()) {
                break;
            }
        }
    }
    
}
