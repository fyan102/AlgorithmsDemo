package org.fyan102.algorithms;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class AStar {
    public IStateRepresent solve(IStateRepresent initState, IStateRepresent goalState) {
        PriorityQueue<IStateRepresent> openSet = new PriorityQueue<>(new Comparator<IStateRepresent>() {
            @Override
            public int compare(IStateRepresent iStateRepresent, IStateRepresent t1) {
                double f1 = iStateRepresent.cost() + iStateRepresent.heuristics(goalState);
                double f2 = t1.cost() + t1.heuristics(goalState);
                return Double.compare(f1, f2);
            }
        });
        openSet.add(initState);
        ArrayList<IStateRepresent> closedSet = new ArrayList<>();
        while (!openSet.isEmpty()) {
            IStateRepresent current = openSet.poll();
            System.out.println(current);
            closedSet.add(current);
            if (current.equals(goalState)) {
                return current;
            }
            ArrayList<Method> actions = current.operations();
            for (Method action : actions) {
                try {
                    IStateRepresent newState = (IStateRepresent) action.invoke(current);
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
                    if (!exist) {
                        openSet.add(newState);
                    }
                }
                catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
