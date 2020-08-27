package org.fyan102.algorithms.interfaces;

import org.fyan102.algorithms.algorithm.Node;

/**
 * ISearchTree interface represents a search tree. Classes implements this interface
 * should be able to add a new state to the search tree, and it should also be able to
 * return the root node
 *
 * @param <T> the data type of the state. T should implements IStateRepresent interface.
 * @author Fan
 * @version 1.0
 */
public interface ISearchTree<T extends IStateRepresent> {
    /**
     * Implementation of add() method should be able to add a new state to the
     * search tree.
     *
     * @param newState The new state that needs to be added into the search tree.
     */
    void add(T newState);
    
    /**
     * Implementation of getRoot() should return the root of the search tree.
     *
     * @return the root of the search tree.
     */
    Node getRoot();
    
}
