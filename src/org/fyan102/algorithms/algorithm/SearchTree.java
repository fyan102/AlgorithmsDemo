package org.fyan102.algorithms.algorithm;

import org.fyan102.algorithms.Interfaces.ISearchTree;
import org.fyan102.algorithms.Interfaces.IStateRepresent;

import java.util.Stack;

public class SearchTree<T extends IStateRepresent> implements ISearchTree {
    private Node root;

    SearchTree(T rootValue) {
        this.root = new Node(rootValue);
    }

    @Override
    public void add(IStateRepresent newState) {
        Node parent = find(newState.getParent());
        if (parent != null) {
            Node newNode = new Node(newState, parent);
            parent.getChildren().add(newNode);
        }
    }

    private Node find(IStateRepresent value) {
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.empty()) {
            Node current = stack.pop();
            if (current.getValue().equals(value)) {
                return current;
            }
            for (Node node : current.getChildren()) {
                stack.push(node);
            }
        }
        return null;
    }

    @Override
    public Node getRoot() {
        return root;
    }
}
