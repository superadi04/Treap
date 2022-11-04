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

        private Node(K key, V value, int priority) {
            this.key = key;
            this.value = value;
            this.priority = priority;
        }

        private void setLeftChild(Node leftChild) {
            this.leftChild = leftChild;
        }

        private void setRightChild(Node rightChild) {
            this.rightChild = rightChild;
        }

        private void setValue(V value) {
            this.value = value;
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

        @Override
        public boolean equals(Object o) {
            Node curr = (Node) o;
            return this.key.equals(curr.getKey());
        }
    }

    private Node root;

    public MyTreap() {

    }

    private MyTreap(Node n) {
        this.root = n;
    }

    @Override
    public V lookup(K key) {
        if (key == null) {
            return null;
        }

        return lookupHelper(root, key);
    }

    private V lookupHelper(Node currNode, K key) {
        if (key.compareTo(currNode.getKey()) < 0) {
            lookupHelper(currNode.getLeftChild(), key);
        } else if (key.compareTo(currNode.getKey()) > 0) {
            lookupHelper(currNode.getLeftChild(), key);
        } else {
            return currNode.getValue();
        }

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
            //searchRotate(placedParent, root);
        }

    }

    // Helper method to place node in Treap according to BST property
    // Return parent of Node space to be inserted
    private void placeNodeBST(Node insertedNode, Node currNode) {
        K currKey = currNode.getKey();
        K insertedKey = insertedNode.getKey();

        if (insertedKey.compareTo(currKey) < 0) {
            if (currNode.getLeftChild() == null) {
                currNode.setLeftChild(insertedNode);
            } else {
                placeNodeBST(insertedNode, currNode.getLeftChild());
            }

            if (currNode.getPriority() < currNode.getLeftChild().getPriority()) {
                Node rotatedHead = rotateRight(currNode);
                if (currNode.equals(root)) {
                    root = rotatedHead;
                } else {
                    currNode.setLeftChild(rotatedHead);
                }
            }
        } else if (insertedKey.compareTo(currKey) > 0) {
            if (currNode.getRightChild() == null) {
                currNode.setRightChild(insertedNode);
            } else {
                placeNodeBST(insertedNode, currNode.getRightChild());
            }

            if (currNode.getPriority() < currNode.getRightChild().getPriority()) {
                Node rotatedHead = rotateLeft(currNode);
                if (currNode.equals(root)) {
                    root = rotatedHead;
                } else {
                    currNode.setRightChild(rotatedHead);
                }
            }
        } else {
            currNode.setValue(insertedNode.getValue());
        }
    }

    // Given parent root where x is the left child of y:
    // Rotate right around y
    private Node rotateRight(Node a) {
        Node b = a.getLeftChild();
        Node bRightSub = b.getRightChild();
        b.setRightChild(a);
        a.setLeftChild(bRightSub);
        return b;
    }

    // Given parent root where x is the right child of y:
    // Rotate left around y
    private Node rotateLeft(Node a) {
        Node b = a.getRightChild();
        Node bLeftSub = b.getLeftChild();
        b.setLeftChild(a);
        a.setRightChild(bLeftSub);
        return b;
    }


    @Override
    public V remove(K key) {
        return removeHelper(root, key);
    }


    private V removeHelper(Node currNode, K deleteKey) {
        K currKey = currNode.getKey();

        if (deleteKey.compareTo(currKey) < 0) {
            removeHelper(currNode.getLeftChild(), deleteKey);
        } else if (deleteKey.compareTo(currKey) > 0) {
            removeHelper(currNode.getRightChild(), deleteKey);
        } else {
            if (currNode.getLeftChild() != null && currNode.getRightChild() != null) {
                if (currNode.getLeftChild().getPriority() < currNode.getRightChild().getPriority()) {
                    rotateRight(currNode);
                    //if (currNode.getRightChild())
                } else {
                    rotateLeft(currNode);
                }
            } else if (currNode.getLeftChild() != null) {
                rotateRight(currNode);
            } else if (currNode.getRightChild() != null) {
                rotateLeft(currNode);
            }
        }

        return null;
    }

    // NEED TO IMPLEMENT
    private boolean isLeafNode(Node currNode) {
        return false;
    }

    @Override
    public Treap[] split(K key) {
        Treap[] splits = new Treap[2];

        if (root == null) {
            return splits;
        }

        Node n = new Node(key, null, Integer.MAX_VALUE);

        splits[0] = new MyTreap(root.getLeftChild());
        splits[1] = new MyTreap(root.getRightChild());

        // Remove the added node

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
