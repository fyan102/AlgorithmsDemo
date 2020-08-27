package org.fyan102.algorithms.data_structure;

import org.fyan102.algorithms.interfaces.IStateRepresent;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private IStateRepresent value;
    private List<Node> children;

    Node(IStateRepresent value) {
        this.value = value;
        children = new ArrayList<>();
    }

    public List<Node> getChildren() {
        return children;
    }

    public IStateRepresent getValue() {
        return value;
    }
}
