package org.fyan102.algorithms.algorithm;

import org.fyan102.algorithms.Interfaces.IAction;
import org.fyan102.algorithms.Interfaces.IGraphSearch;
import org.fyan102.algorithms.Interfaces.ISearchTree;
import org.fyan102.algorithms.Interfaces.IStateRepresent;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class AStar implements IGraphSearch {
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

    /**
     * Check whether a new state has already been inside the open set or the closed set
     *
     * @param newState the new state to be checked
     * @return true if the new state is inside the open set or the closed set
     */
    private boolean exist(IStateRepresent newState) {
        boolean exist = false;
        for (IStateRepresent state : closedSet) {
            if (state.equals(newState)) {
                exist = true;
                break;
            }
        }
        for (IStateRepresent state : openSet) {
            if (state.equals(newState)) {
                exist = true;
                break;
            }
        }
        return exist;
    }

    public ISearchTree<IStateRepresent> getSearchTree() {
        return searchTree;
    }

    @Override
    public void reset() {
        openSet.clear();
        openSet.add(initState);
        closedSet.clear();
        searchTree = new SearchTree<>(initState);
    }

    public void solve() {
        reset();
        while (!openSet.isEmpty()) {
            IStateRepresent state = solveOneStep();
            if (state.isGoalState()) {
                break;
            }
        }
    }

    @Override
    public IStateRepresent solveOneStep() {
        IStateRepresent current = openSet.poll();
        closedSet.add(current);
        if (current.isGoalState()) {
            return current;
        }
        ArrayList<IAction> IActions = current.operations();
        for (IAction IAction : IActions) {
            IStateRepresent newState = IAction.method();
            if (!exist(newState)) {
                openSet.add(newState);
                searchTree.add(newState);
            }
        }
        return current;
    }
}
