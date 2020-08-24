package org.fyan102.algorithms.algorithm;

import org.fyan102.algorithms.Interfaces.IAction;
import org.fyan102.algorithms.Interfaces.IGraphSearch;
import org.fyan102.algorithms.Interfaces.ISearchTree;
import org.fyan102.algorithms.Interfaces.IStateRepresent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class AStar implements IGraphSearch {
    private PriorityQueue<IStateRepresent> openSet;
    private ArrayList<IStateRepresent> closedSet;

    public AStar() {
        openSet = new PriorityQueue<>(new Comparator<IStateRepresent>() {
            @Override
            public int compare(IStateRepresent iStateRepresent, IStateRepresent t1) {
                double f1 = iStateRepresent.cost() + iStateRepresent.heuristic();
                double f2 = t1.cost() + t1.heuristic();
                return Double.compare(f1, f2);
            }
        });
        closedSet = new ArrayList<>();
    }

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

    public ISearchTree<IStateRepresent> solve(IStateRepresent initState) {
        ISearchTree<IStateRepresent> searchTree = new SearchTree<IStateRepresent>(initState);
        openSet.add(initState);
        while (!openSet.isEmpty()) {
            IStateRepresent current = openSet.poll();
            closedSet.add(current);
            if (current.isGoalState()) {
                break;
            }
            ArrayList<IAction> IActions = current.operations();
            for (IAction IAction : IActions) {
                IStateRepresent newState = IAction.method();
                if (!exist(newState)) {
                    openSet.add(newState);
                    searchTree.add(newState);
                }
            }
        }
        return searchTree;
    }

    @Override
    public ISearchTree<IStateRepresent> solveOneStep(ISearchTree<IStateRepresent> searchTree,
                                                     IStateRepresent current) {
        closedSet.add(current);
        if (current.isGoalState()) {
            return searchTree;
        }
        ArrayList<IAction> IActions = current.operations();
        for (IAction IAction : IActions) {
            IStateRepresent newState = IAction.method();
            if (!exist(newState)) {
                openSet.add(newState);
                searchTree.add(newState);
            }
        }
        return searchTree;
    }
}
