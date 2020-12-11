package org.fyan102.algorithms.algorithm;

import org.fyan102.algorithms.data_structure.SearchTree;
import org.fyan102.algorithms.interfaces.IAction;
import org.fyan102.algorithms.interfaces.IGraphSearchSolver;
import org.fyan102.algorithms.interfaces.ISearchTree;
import org.fyan102.algorithms.interfaces.IStateRepresent;

import java.util.ArrayList;
import java.util.Collection;

public class BreadthFirstSearch implements IGraphSearchSolver {
    private ArrayList<IStateRepresent> openSet;
    private ArrayList<IStateRepresent> closedSet;
    private SearchTree<IStateRepresent> searchTree;
    private IStateRepresent initState;

    public BreadthFirstSearch(IStateRepresent initState) {
        this.initState = initState;
        openSet = new ArrayList<>();
        openSet.add(this.initState);
        closedSet = new ArrayList<>();
        searchTree = new SearchTree<>(this.initState);
    }

    /**
     * @return the closed set
     */
    @Override
    public Collection<IStateRepresent> getClosedSet() {
        return closedSet;
    }

    @Override
    public Collection<IStateRepresent> getOpenSet() {
        return openSet;
    }
    
    @Override
    public ISearchTree<IStateRepresent> getSearchTree() {
        return searchTree;
    }

    @Override
    public void reset() {
        openSet.clear();
        openSet.add(initState);
        closedSet.clear();
        searchTree = new SearchTree<>(this.initState);
    }

    @Override
    public void setInitState(IStateRepresent initState) {
        this.initState = initState;
    }
    
    @Override
    public IStateRepresent solveOneStep() {
        IStateRepresent current = openSet.remove(0);
        closedSet.add(current);
        assert current != null;
        if (current.isGoalState()) {
            return current;
        }
        for (IAction possibleAction : current.operations()) {
            IStateRepresent newState = possibleAction.method();
            if (notExist(newState)) {
                openSet.add(newState);
                searchTree.add(newState);
            }
        }
        return current;
    }
}
