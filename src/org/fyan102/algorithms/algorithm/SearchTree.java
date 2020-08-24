package org.fyan102.algorithms.algorithm;

import org.fyan102.algorithms.Interfaces.ISearchTree;
import org.fyan102.algorithms.Interfaces.IStateRepresent;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SearchTree<T extends IStateRepresent> implements ISearchTree {
    private Node root;

    public SearchTree(T rootValue) {
        this.root = new Node(rootValue);
    }

    @Override
    public void add(IStateRepresent newState) {
        Node parent = find(newState.getParent());
        if (parent != null) {
            Node newNode = new Node(newState, parent);
            parent.children.add(newNode);
        }
    }

    public Node find(IStateRepresent value) {
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.empty()) {
            Node current = stack.pop();
            if (current.value.equals(value)) {
                return current;
            }
            for (Node node : current.children) {
                stack.push(node);
            }
        }
        return null;
    }

    private class Node {
        private IStateRepresent value;
        private Node parent;
        private List<Node> children;
        private int hierachy;

        public Node(IStateRepresent value) {
            this.value = value;
            parent = null;
            children = new ArrayList<>();
            hierachy = 0;
        }

        public Node(IStateRepresent value, Node parent) {
            this.value = value;
            this.parent = parent;
            children = new ArrayList<>();
            hierachy = parent.hierachy + 1;
        }
    }
}
