package org.fyan102.algorithms.algorithm;

import org.fyan102.algorithms.Interfaces.IStateRepresent;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private IStateRepresent value;
    private Node parent;
    private List<Node> children;

    public Node(IStateRepresent value) {
        this.value = value;
        parent = null;
        children = new ArrayList<>();
    }

    public Node(IStateRepresent value, Node parent) {
        this.value = value;
        this.parent = parent;
        children = new ArrayList<>();
    }

    public List<Node> getChildren() {
        return children;
    }

    public Node getParent() {
        return parent;
    }

    public IStateRepresent getValue() {
        return value;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setValue(IStateRepresent value) {
        this.value = value;
    }
}
