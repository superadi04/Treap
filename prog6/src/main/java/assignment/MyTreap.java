package assignment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyTreap<K extends Comparable<K>, V> implements Treap<K, V> {

    private class Node {
        private Node leftChild;
        private Node rightChild;
        private Node parent;
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

        private void setParent(Node parent) {
            this.parent = parent;
        }

        private Node getParent() {
            return this.parent;
        }

        private void setLeftChild(Node leftChild) {
            this.leftChild = leftChild;
            if (leftChild != null) {
                leftChild.setParent(this);
            }

        }

        private void setRightChild(Node rightChild) {
            this.rightChild = rightChild;
            if (rightChild != null) {
                rightChild.setParent(this);
            }
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

        private boolean isLeafNode() {
            return leftChild == null && rightChild == null;
        }

        @Override
        public boolean equals(Object o) {
            Node curr = (Node) o;
            return this.key.equals(curr.getKey());
        }

        @Override
        public String toString() {
            return "[" + this.getPriority() + "] <" + this.getKey() + ", " + this.getValue();
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

        if (this.root == null) {
            this.root = insertedNode;
        } else {
            placeNodeBST(insertedNode, this.root);
        }

    }

    // Helper method to place node in Treap according to BST property
    // Return parent of Node space to be inserted
    private void placeNodeBST(Node insertedNode, Node currNode) {
        if (insertedNode.getKey().compareTo(currNode.getKey()) < 0) {
            if (currNode.getLeftChild() == null) {
                currNode.setLeftChild(insertedNode);
            } else {
                placeNodeBST(insertedNode, currNode.getLeftChild());
            }

            if (currNode.getLeftChild().getPriority() > currNode.getPriority()) {
                rotateRight(currNode);
            }
        } else if (insertedNode.getKey().compareTo(currNode.getKey()) > 0) {
            if (currNode.getRightChild() == null) {
                currNode.setRightChild(insertedNode);
            } else {
                placeNodeBST(insertedNode, currNode.getRightChild());
            }

            if (currNode.getRightChild().getPriority() > currNode.getPriority()) {
                rotateLeft(currNode);
            }
        } else {
            currNode.setValue(insertedNode.getValue());
        }
    }

    private void updateParent(Node parent, Node pivot, Node pivotChild) {
        if (parent == null) {
            this.root = pivotChild;
            this.root.setParent(null);
        } else if (parent.getLeftChild() != null && parent.getLeftChild().getKey().equals(pivot.getKey())) {
            parent.setLeftChild(pivotChild);
        } else {
            parent.setRightChild(pivotChild);
        }
    }

    // Given parent root where x is the left child of y:
    // Rotate right around y
    private void rotateRight(Node pivot) {
        Node parent = pivot.getParent();
        Node pivotLeft = pivot.getLeftChild();
        pivot.setLeftChild(pivotLeft.getRightChild());
        pivotLeft.setRightChild(pivot);
        updateParent(parent, pivot, pivotLeft);
    }

    // Given parent root where x is the right child of y:
    // Rotate left around y
    private void rotateLeft(Node pivot) {
        Node parent = pivot.getParent();
        Node pivotRight = pivot.getRightChild();
        pivot.setRightChild(pivotRight.getLeftChild());
        pivotRight.setLeftChild(pivot);
        updateParent(parent, pivot, pivotRight);
    }


    @Override
    public V remove(K key) {
        return removeHelper(root, key);
    }

    // Recursive helper method for remove function
    private V removeHelper(Node currNode, K deleteKey) {
        if (currNode == null) {
            return null;
        }

        if (deleteKey.compareTo(currNode.getKey()) < 0) {
            removeHelper(currNode.getLeftChild(), deleteKey);
        } else if (deleteKey.compareTo(currNode.getKey()) > 0) {
            removeHelper(currNode.getRightChild(), deleteKey);
        } else {
            if (currNode.isLeafNode()) {
                removeLeaf(currNode);
            } else if (currNode.getLeftChild() != null && currNode.getRightChild() != null) {
                if (currNode.getLeftChild().getPriority() > currNode.getRightChild().getPriority()) {
                    rotateRight(currNode);
                } else {
                    rotateLeft(currNode);
                }
                removeHelper(currNode, deleteKey);
            } else if (currNode.getLeftChild() != null) {
                rotateRight(currNode);
                removeHelper(currNode, deleteKey);
            } else {
                rotateLeft(currNode);
                removeHelper(currNode, deleteKey);
            }

            return currNode.getValue();
        }

        return null;
    }

    // Helper method to remove leaf node of Treap
    private void removeLeaf(Node currNode) {
        Node parent = currNode.getParent();

        if (parent == null) {
            this.root = null;
        } else if (parent.getLeftChild() != null && parent.getLeftChild().getKey().equals(currNode.getKey())) {
            parent.setLeftChild(null);
        } else {
            parent.setRightChild(null);
        }
    }


    @Override
    public Treap[] split(K key) {
        Treap[] splits = new MyTreap[2];

        if (root == null) {
            return splits;
        }

        Node n = new Node(key, null, Integer.MAX_VALUE);
        this.placeNodePriority(n, root);

        splits[0] = new MyTreap(root.getLeftChild());
        splits[1] = new MyTreap(root.getRightChild());

        return splits;
    }

    private void placeNodePriority(Node insertedNode, Node currNode) {
        if (insertedNode.getKey().compareTo(currNode.getKey()) <= 0) {
            if (currNode.getLeftChild() == null) {
                currNode.setLeftChild(insertedNode);
            } else {
                placeNodePriority(insertedNode, currNode.getLeftChild());
            }

            if (currNode.getLeftChild().getPriority() > currNode.getPriority()) {
                rotateRight(currNode);
            }
        } else {
            if (currNode.getRightChild() == null) {
                currNode.setRightChild(insertedNode);
            } else {
                placeNodePriority(insertedNode, currNode.getRightChild());
            }

            if (currNode.getRightChild().getPriority() > currNode.getPriority()) {
                rotateLeft(currNode);
            }
        }
    }

    @Override
    public void join(Treap t) {
        MyTreap myT = (MyTreap) t;
        Node tRoot = myT.root;
        if (root != null) {
            Node dummy = new Node(root.key, root.value);
            dummy.setLeftChild(this.root);
            dummy.setRightChild(tRoot);
            this.root = dummy;
            remove(root.key);
        } else {
            this.root = tRoot;
        }
    }

    @Override
    public void meld(Treap t) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void difference(Treap t) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
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
        nodes.add(currNode);
        iteratorHelper(nodes, currNode.getRightChild());
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        this.toStringHelper(output, root, "");
        return output.toString();
    }

    // Helper method for toString(), pre-order traversal
    private void toStringHelper(StringBuilder output, Node curr, String tab) {
        if (curr == null) {
            return;
        }

        output.append(tab + curr.toString() + ">\n");

        toStringHelper(output, curr.getLeftChild(), tab + "    ");
        toStringHelper(output, curr.getRightChild(), tab + "    ");
    }

    @Override
    public double balanceFactor() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
