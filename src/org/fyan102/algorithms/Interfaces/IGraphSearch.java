package org.fyan102.algorithms.Interfaces;

import org.fyan102.algorithms.algorithm.SearchTree;

public interface IGraphSearch {
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
