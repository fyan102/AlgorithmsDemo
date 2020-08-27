package org.fyan102.algorithms.interfaces;

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
    
    /**
     * The solve method will take the initial state and solve the problem
     */
    void solve();
    
    /**
     * The solveOneStep method takes the current state and solve the problem by one step
     *
     * @return the next state of the problem
     */
    IStateRepresent solveOneStep();
}
