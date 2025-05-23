package org.fyan102.algorithms.algorithm;

import org.fyan102.algorithms.data_structure.SearchTree;
import org.fyan102.algorithms.interfaces.IAction;
import org.fyan102.algorithms.interfaces.IGraphSearchSolver;
import org.fyan102.algorithms.interfaces.IStateRepresent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.PriorityQueue;

/**
 * AStar class is an implementation of A Star algorithm
 *
 * @author Fan
 * @version 1.0
 */
public class AStar implements IGraphSearchSolver {
    private PriorityQueue<IStateRepresent> openSet;
    private ArrayList<IStateRepresent> closedSet;
    private SearchTree<IStateRepresent> searchTree;
    private IStateRepresent initState;
    
    /**
     * The constructor of AStar class.
     * Initialize the open set and the closed set
     */
    public AStar(IStateRepresent initState) {
        openSet = new PriorityQueue<>((iStateRepresent, t1) -> {
            double f1 = iStateRepresent.cost() + iStateRepresent.heuristic();
            double f2 = t1.cost() + t1.heuristic();
            return Double.compare(f1, f2);
        });
        this.initState = initState;
        openSet.add(initState);
        closedSet = new ArrayList<>();
        searchTree = new SearchTree<>(initState);
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
    public SearchTree<IStateRepresent> getSearchTree() {
        return searchTree;
    }
    
    @Override
    public void reset() {
        openSet.clear();
        openSet.add(initState);
        closedSet.clear();
        searchTree = new SearchTree<>(initState);
    }
    
    protected void setOpenSet(PriorityQueue<IStateRepresent> newOpenSet) {
        openSet = newOpenSet;
    }
    
    @Override
    public void setInitState(IStateRepresent initState) {
        this.initState = initState;
    }
    
    @Override
    public IStateRepresent solveOneStep() {
        IStateRepresent current = openSet.poll();
        closedSet.add(current);
        assert current != null;
        if (current.isGoalState()) {
            return current;
        }
        ArrayList<IAction> IActions = current.operations();
        for (IAction IAction : IActions) {
            IStateRepresent newState = IAction.method();
            if (notExist(newState)) {
                openSet.add(newState);
                searchTree.add(newState);
            }
        }
        return current;
    }
}
