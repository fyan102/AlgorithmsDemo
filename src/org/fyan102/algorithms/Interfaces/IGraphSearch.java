package org.fyan102.algorithms.Interfaces;

import org.fyan102.algorithms.algorithm.SearchTree;

public interface IGraphSearch {
    /**
     * The solve method will take the initial state and solve the problem
     *
     * @param initState the init state
     * @return the goal state with cost and heuristic updated
     */
    ISearchTree<IStateRepresent> solve(IStateRepresent initState);

    /**
     * The solveOneStep method takes the current state and solve the problem by one step
     *
     * @param searchTree   the current search tree
     * @param currentState the current state
     * @return the next state of the problem
     */
    ISearchTree<IStateRepresent> solveOneStep(ISearchTree<IStateRepresent> searchTree, IStateRepresent currentState);
}
