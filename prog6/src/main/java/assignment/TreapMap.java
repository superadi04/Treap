package assignment;

import java.util.*;

public class TreapMap<K extends Comparable<K>, V> implements Treap<K, V> {

    // Internal node class
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
        public String toString() {
            return "[" + this.getPriority() + "] <" + this.getKey() + ", " + this.getValue() + ">";
        }
    }

    // Root node of tree
    private Node root;

    private boolean modified;

    public TreapMap() {

    }

    // Private constructor for internal usage
    private TreapMap(Node n) {
        this.root = n;
    }

    @Override
    public V lookup(K key) {
        if (key == null) {
            return null;
        }

        return lookupHelper(root, key);
    }

    // Recursive helper method for lookup function
    private V lookupHelper(Node currNode, K key) {
        if (currNode == null) {
            return null;
        }

        if (key.compareTo(currNode.getKey()) < 0) {
            return lookupHelper(currNode.getLeftChild(), key);
        } else if (key.compareTo(currNode.getKey()) > 0) {
            return lookupHelper(currNode.getRightChild(), key);
        } else {
            return currNode.getValue();
        }
    }

    @Override
    public void insert(K key, V value) {
        // Sanity check if key or value is null
        if (key == null || value == null) {
            return;
        }

        Node insertedNode = new Node(key, value);

        if (this.root == null) { // Check if there are no nodes in the tree
            this.root = insertedNode;
        } else {
            // Recursive call
            placeNodeBST(insertedNode, this.root);
        }

        modified = true;
    }

    // Helper method to place node in Treap according to BST property
    private void placeNodeBST(Node insertedNode, Node currNode) {
        if (insertedNode.getKey().compareTo(currNode.getKey()) < 0) {
            if (currNode.getLeftChild() == null) {
                currNode.setLeftChild(insertedNode);
            } else {
                // Recursive call
                placeNodeBST(insertedNode, currNode.getLeftChild());
            }

            // Determine whether to percolate upwards w/ recursive backtracking
            if (currNode.getLeftChild().getPriority() > currNode.getPriority()) {
                rotateRight(currNode);
            }
        } else if (insertedNode.getKey().compareTo(currNode.getKey()) > 0) {
            if (currNode.getRightChild() == null) {
                currNode.setRightChild(insertedNode);
            } else {
                // Recursive call
                placeNodeBST(insertedNode, currNode.getRightChild());
            }

            // Determine whether to percolate upwards w/ recursive backtracking
            if (currNode.getRightChild().getPriority() > currNode.getPriority()) {
                rotateLeft(currNode);
            }
        } else { // If node is already present, update value
            currNode.setValue(insertedNode.getValue());
        }
    }

    // Helper method used after rotation to fix rotated node to parent node
    private void updateParent(Node parent, Node pivot, Node pivotChild) {
        if (parent == null) {
            this.root = pivotChild;
            this.root.setParent(null);
        } else if (parent.getLeftChild() != null && parent.getLeftChild().getKey().equals(pivot.getKey())) {
            // Check if the parent's left child equals the current node
            parent.setLeftChild(pivotChild);
        } else {
            // Check if the parent's right child equals the current node
            parent.setRightChild(pivotChild);
        }
    }

    // Perform a right rotation around pivot node
    private void rotateRight(Node pivot) {
        Node parent = pivot.getParent();
        Node pivotLeft = pivot.getLeftChild();
        pivot.setLeftChild(pivotLeft.getRightChild());
        pivotLeft.setRightChild(pivot);
        updateParent(parent, pivot, pivotLeft);
    }

    // Perform a left rotation around the pivot node
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

        V currValue;

        if (deleteKey.compareTo(currNode.getKey()) < 0) {
            currValue = removeHelper(currNode.getLeftChild(), deleteKey);
        } else if (deleteKey.compareTo(currNode.getKey()) > 0) {
            currValue = removeHelper(currNode.getRightChild(), deleteKey);
        } else {
            // We have found the node to be removed
            currValue = currNode.getValue();

            if (currNode.isLeafNode()) {
                removeLeaf(currNode);
                modified = true;
            } else if (currNode.getLeftChild() != null && currNode.getRightChild() != null) {
                // Both child nodes are not null
                if (currNode.getLeftChild().getPriority() > currNode.getRightChild().getPriority()) {
                    rotateRight(currNode);
                } else {
                    rotateLeft(currNode);
                }
                removeHelper(currNode, deleteKey);
            } else if (currNode.getLeftChild() != null) {
                // If only left child is present, we rotate right
                rotateRight(currNode);
                removeHelper(currNode, deleteKey);
            } else {
                // If only right child is present, we rotate left
                rotateLeft(currNode);
                removeHelper(currNode, deleteKey);
            }
        }

        // Node is not found in tree
        return currValue;
    }

    // Helper method to remove a leaf node in Treap
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
    public Treap<K, V>[] split(K key) {
        Treap<K, V>[] splits = new TreapMap[2];

        // Sanity check if root or key is null
        if (root == null || key == null) {
            return splits;
        }

        // Add new dummy node with Infinite priority
        Node n = new Node(key, null, Integer.MAX_VALUE);
        placeNodePriority(n, root);

        // Split into 2 Treaps
        splits[0] = new TreapMap(root.getLeftChild());
        splits[1] = new TreapMap(root.getRightChild());

        modified = true;

        return splits;
    }

    // Helper method for split method to insert node as root
    // Additionally accounts for split calls w/ duplicate key
    private void placeNodePriority(Node insertedNode, Node currNode) {
        if (insertedNode.getKey().compareTo(currNode.getKey()) <= 0) {
            if (currNode.getLeftChild() == null) {
                currNode.setLeftChild(insertedNode);
            } else {
                placeNodePriority(insertedNode, currNode.getLeftChild());
            }

            // Percolate upwards, recursive backtracking
            if (currNode.getLeftChild().getPriority() > currNode.getPriority()) {
                rotateRight(currNode);
            }
        } else {
            if (currNode.getRightChild() == null) {
                currNode.setRightChild(insertedNode);
            } else {
                placeNodePriority(insertedNode, currNode.getRightChild());
            }

            // Percolate upwards, recursive backtracking
            if (currNode.getRightChild().getPriority() > currNode.getPriority()) {
                rotateLeft(currNode);
            }
        }
    }

    @Override
    public void join(Treap t) {
        // Sanity check if t is null
        if (t == null) {
            return;
        }

        TreapMap myT = (TreapMap) t;
        Node tRoot = myT.root;

        if (root != null) {
            // Set dummy node as root and then remove dummy node
            Node dummy = new Node(root.key, root.value);
            dummy.setLeftChild(this.root);
            dummy.setRightChild(tRoot);
            this.root = dummy;
            remove(root.key);
        } else {
            // If tree is empty, we simply set the parameter to this tree
            this.root = tRoot;
        }

        modified = true;
    }

    @Override
    public void meld(Treap t) throws UnsupportedOperationException {
        if (t == null) {
            return;
        }

        TreapMap myT = (TreapMap) t;
        Node tRoot = myT.root;
        this.root = meldHelper(this.root, tRoot);
    }

    private void swap(Node a, Node b) {
        Node c = a;


    }

    @Override
    public void difference(Treap t) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        List<K> nodes = new ArrayList<>();
        iteratorHelper(nodes, root);
        modified = false;
        return new Iterator<K> () {
            private int count; // Current element within sorted collection

            @Override
            public boolean hasNext() {
                if (count >= nodes.size()) {
                    return false;
                }
                return true;
            }

            @Override
            public K next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                // Check if Treap was modified during operation or not
                if (modified) {
                    throw new ConcurrentModificationException();
                } else {
                    return nodes.get(count++);
                }
            }
        };
    }

    // Helper method to acquire all keys in sorted order
    private void iteratorHelper(List<K> nodes, Node currNode) {
        if (currNode == null) {
            return;
        }

        // In order traversal
        iteratorHelper(nodes, currNode.getLeftChild());
        nodes.add(currNode.getKey());
        iteratorHelper(nodes, currNode.getRightChild());
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        toStringHelper(output, root, "");
        return output.toString();
    }

    // Helper method for toString(), pre-order traversal
    private void toStringHelper(StringBuilder output, Node curr, String tab) {
        if (curr == null) {
            return;
        }

        output.append(tab + curr + "\n");

        toStringHelper(output, curr.getLeftChild(), tab + "\t");
        toStringHelper(output, curr.getRightChild(), tab + "\t");
    }

    // Used to measure how balanced tree is
    @Override
    public double balanceFactor() {
        if (root == null || root.isLeafNode()) {
            return 1;
        }
        int[] ans = new int[2];
        ans[0] = Integer.MAX_VALUE;
        balanceFactorHelper(0, root, ans);

        return (double) ans[1] / ans[0];
    }

    public void balanceFactorHelper(int currHeight, Node currNode, int[] ans) {
        if (currNode == null) {
            return;
        }

        // Find minimum and maximum height of Treap
        if (currNode.isLeafNode()) {
            ans[0] = Math.min(ans[0], currHeight);
            ans[1] = Math.max(ans[1], currHeight);
        } else {
            balanceFactorHelper(currHeight + 1, currNode.getLeftChild(), ans);
            balanceFactorHelper(currHeight + 1, currNode.getRightChild(), ans);
        }
    }
}
