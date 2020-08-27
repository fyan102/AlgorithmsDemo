package org.fyan102.algorithms.algorithm;

import org.fyan102.algorithms.interfaces.IGraphSearchSolver;
import org.fyan102.algorithms.interfaces.ISearchTree;
import org.fyan102.algorithms.interfaces.IStateRepresent;

import java.util.Collection;

public class IDS implements IGraphSearchSolver {
    @Override
    public ISearchTree<IStateRepresent> getSearchTree() {
        return null;
    }
    
    @Override
    public void reset() {
    
    }
    
    @Override
    public IStateRepresent solveOneStep() {
        return null;
    }
    
    @Override
    public Collection<IStateRepresent> getClosedSet() {
        return null;
    }
    
    @Override
    public Collection<IStateRepresent> getOpenSet() {
        return null;
    }
    
    @Override
    public void setInitState(IStateRepresent initState) {
    
    }
}
