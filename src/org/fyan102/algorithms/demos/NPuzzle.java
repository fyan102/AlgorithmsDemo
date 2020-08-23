package org.fyan102.algorithms.demos;

import org.fyan102.algorithms.IStateRepresent;

import java.util.ArrayList;

public class NPuzzle implements IStateRepresent {
    @Override
    public ArrayList operations() {
        return null;
    }

    @Override
    public boolean constraints() {
        return false;
    }

    @Override
    public double heuristics() {
        return 0;
    }

    @Override
    public double cost() {
        return 0;
    }
}
