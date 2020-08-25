package org.fyan102.algorithms.interfaces;

public interface IGraphSearchSolver {
    ISearchTree<IStateRepresent> getSearchTree();

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
