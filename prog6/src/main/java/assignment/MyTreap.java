package assignment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyTreap<K extends Comparable<K>, V> implements Treap<K, V> {

    private class Node {
        private Node leftChild;
        private Node rightChild;
        private K key;
        private V value;
        private int priority;

        private Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.priority = (int) (Math.random() * Treap.MAX_PRIORITY + 1);
        }

        private void setLeftChild(Node leftChild) {
            this.leftChild = leftChild;
        }

        private void setRightChild(Node rightChild) {
            this.rightChild = rightChild;
        }

        private Node getLeftChild() {
            return this.leftChild;
        }

        private Node getRightChild() {
            return this.rightChild;
        }

        private int getPriority() {
            return this.priority;
        }

        private K getKey() {
            return this.key;
        }

        private V getValue() {
            return this.value;
        }
    }

    private Node root;

    public MyTreap() {

    }

    @Override
    public V lookup(K key) {
        if (key == null) {
            return null;
        }
        return lookupHelper(root, key);
    }

    private V lookupHelper(Node currNode, K key) {
        if (currNode == null) {
            return null;
        } else if (currNode.getKey().equals(key)) {
            return currNode.getValue();
        }

        lookupHelper(currNode.getLeftChild(), key);
        lookupHelper(currNode.getRightChild(), key);

        return null;
    }

    @Override
    public void insert(K key, V value) {
        Node insertedNode = new Node(key, value);

        // Check for edge case when root is null
        if (this.root == null) {
            this.root = insertedNode;
        } else {
            placeNodeBST(insertedNode, root);
        }

    }

    // Helper method to place node in Treap according to BST property
    // Return parent of Node space to be inserted
    // NEED TO ACCOUNT FOR SAME KEYS
    private Node placeNodeBST(Node insertedNode, Node currNode) {
        K currKey = currNode.getKey();
        K insertedKey = insertedNode.getKey();

        if (currKey.compareTo(insertedKey) < 0) {
            if (currNode.getLeftChild() == null) {
                currNode.setLeftChild(insertedNode);
                return currNode;
            } else {
                placeNodeBST(insertedNode, currNode.getLeftChild());
            }
        } else if (currKey.compareTo(insertedKey) > 0) {
            if (currNode.getRightChild() == null) {
                currNode.setRightChild(insertedNode);
                return currNode;
            } else {
                placeNodeBST(insertedNode, currNode.getRightChild());
            }
        }

        return null;
    }

    private void searchRotate(Node nodeToRotate, Node curr) {
        searchRotate(nodeToRotate, curr.getLeftChild());
        searchRotate(nodeToRotate, curr.getRightChild());

        Node leftChild = curr.getLeftChild();
        Node rightChild = curr.getRightChild();

        if (leftChild != null && (leftChild.equals(nodeToRotate) || curr.getPriority() < leftChild.getPriority())) {
            rotateRight(curr.getLeftChild());
        } else if (rightChild != null && (rightChild.equals(nodeToRotate) || curr.getPriority() < rightChild.getPriority())) {
            rotateLeft(curr.getRightChild());
        }

    }

    // Given parent root where x is the left child of y:
    // Rotate right around y
    private void rotateRight(Node parent) {
        Node a = parent.getLeftChild();
        Node b = a.getLeftChild();
        Node bRightSub = b.getRightChild();
        b.setRightChild(a);
        a.setLeftChild(bRightSub);
        parent.setLeftChild(b);
    }

    // Given parent root where x is the right child of y:
    // Rotate left around y
    private void rotateLeft(Node parent) {
        Node a = parent.getRightChild();
        Node b = a.getLeftChild();
        Node bLeftSub = b.getLeftChild();
        b.setLeftChild(a);
        a.setRightChild(bLeftSub);
        parent.setRightChild(b);
    }

    @Override
    public V remove(K key) {

    }

    private void removeHelper(Node curr, K key) {
        if (curr == null) {
            return;
        }

        //removeHelper(curr.getLeftChild(), key);
        //removeHelper(curr.getRightChild(), key);

        Node leftChild = curr.getLeftChild();
        Node rightChild = curr.getRightChild();

        if (leftChild != null && (leftChild.equals(nodeToRotate) || curr.getPriority() < leftChild.getPriority())) {
            rotateRight(curr.getLeftChild());
        } else if (rightChild != null && (rightChild.equals(nodeToRotate) || curr.getPriority() < rightChild.getPriority())) {
            rotateLeft(curr.getRightChild());
        }
    }

    @Override
    public Treap[] split(K key) {
        Treap[] splits = new Treap[2];
        splitSearch(splits, root, key);
        return splits;
    }

    // Helper method
    private void splitSearch(Treap[] splits, Node currNode, K key) {
        if (currNode == null) {
            return;
        }

        if (currNode.getKey().compareTo(key) < 0) {
            splits[0].insert(currNode.getKey(), currNode.getValue());
        } else {
            splits[1].insert(currNode.getKey(), currNode.getValue());
        }

        splitSearch(splits, currNode.getLeftChild(), key);
        splitSearch(splits, currNode.getRightChild(), key);
    }

    @Override
    public void join(Treap t) {

    }

    @Override
    public void meld(Treap t) throws UnsupportedOperationException {

    }

    @Override
    public void difference(Treap t) throws UnsupportedOperationException {

    }

    @Override
    public Iterator iterator() {
        List<Node> nodes = new ArrayList<>();
        iteratorHelper(nodes, root);
        return nodes.iterator();
    }

    // Pre Order Traversal
    private void iteratorHelper(List<Node> nodes, Node currNode) {
        if (currNode == null) {
            return;
        }

        iteratorHelper(nodes, currNode.getLeftChild());
        iteratorHelper(nodes, currNode.getRightChild());

        nodes.add(currNode);
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        toStringHelper(output, root);
        return output.toString();
    }

    // Helper method for toString(), pre-order traversal
    private void toStringHelper(StringBuilder output, Node curr) {
        if (curr == null) {
            return;
        }

        toStringHelper(output, curr.getLeftChild());
        toStringHelper(output, curr.getRightChild());

        output.append("[" + curr.getPriority() + "] <" + curr.getKey() + ", " + curr.getValue() + ">\n");
    }

    @Override
    public double balanceFactor() throws UnsupportedOperationException {
        return 0;
    }
}
