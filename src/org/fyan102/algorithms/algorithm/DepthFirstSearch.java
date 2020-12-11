package org.fyan102.algorithms.algorithm;

import org.fyan102.algorithms.data_structure.SearchTree;
import org.fyan102.algorithms.interfaces.IAction;
import org.fyan102.algorithms.interfaces.IGraphSearchSolver;
import org.fyan102.algorithms.interfaces.ISearchTree;
import org.fyan102.algorithms.interfaces.IStateRepresent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

public class DepthFirstSearch implements IGraphSearchSolver {
    private Stack<IStateRepresent> openSet;
    private ArrayList<IStateRepresent> closedSet;
    private SearchTree<IStateRepresent> searchTree;
    private IStateRepresent initState;
    private int maximumDepth;

    public DepthFirstSearch(IStateRepresent initState, int maxDepth) {
        this.initState = initState;
        openSet = new Stack<>();
        openSet.push(this.initState);
        closedSet = new ArrayList<>();
        searchTree = new SearchTree<>(this.initState);
        maximumDepth = maxDepth;
        if (maximumDepth >= 20) {
            maximumDepth = 20;
        } else if (maximumDepth <= 2) {
            maximumDepth = 2;
        }
    }

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
        openSet.push(initState);
        closedSet.clear();
        searchTree = new SearchTree<>(initState);
    }

    @Override
    public void setInitState(IStateRepresent initState) {
        this.initState = initState;
    }

    public void setMaximumDepth(int maximumDepth) {
        this.maximumDepth = maximumDepth;
    }

    @Override
    public IStateRepresent solveOneStep() {
        IStateRepresent current = openSet.pop();
        closedSet.add(current);
        assert current != null;
        if (current.isGoalState() || current.getDepth() >= maximumDepth) {
            return current;
        }
        List<IAction> actions = current.operations();
        for (int i = actions.size() - 1; i >= 0; i--) {
            IStateRepresent newState = actions.get(i).method();
            if (notExist(newState)) {
                openSet.add(newState);
                searchTree.add(newState);
            }
        }
        return current;
    }
}
