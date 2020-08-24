package org.fyan102.algorithms.Interfaces;

import org.fyan102.algorithms.algorithm.Node;

public interface ISearchTree<T extends IStateRepresent> {
    void add(T newState);

    Node getRoot();

}
