package com.proyecto.estructuras.trees;

/**
 * @author Tomas Bermudez
 *         This class is used when we do not have to do so many insertions/deletions
 *         due to this class make rotations frequently.
 */
public class AVL<T extends Comparable<T>> extends BST<T> implements SelfBalanced<T> {
    /**
     * Constructor (Empty)
     */
    public AVL() {
        super();
    }

    /**
     * Constructor with data
     * 
     * @param data data to insert as root
     */
    public AVL(T data) {
        super(data);
    }

    /**
     * Inserts a new node with the given data into the AVL tree.
     * This method overrides the insertRec method from BinarySearchTree
     */
    @Override
    protected Node<T> postInsert(Node<T> node, T data) {
        return rebalance(node, data, true);
    }

    /**
     * Removes a node with the given data from the AVL tree.
     * This method overrides the removeRec method from BinarySearchTree.
     */
    @Override
    public Node<T> postDelete(Node<T> node, T data) {
        return rebalance(node, data, false);
    }

    /**
     * Calculates the height of the given node.
     * 
     * @param node the node to check
     * @return the height of the node
     */
    public int getBalance(Node<T> node) {
        return node != null ? getHeight(node.left) - getHeight(node.right) : 0;
    }

    @Override
    public Node<T> rebalance(Node<T> node, T data, boolean ins) {
        updateHeight(node);
        int balance = getBalance(node);

        if (balance > 1) {
            if ((data.compareTo(node.left.data) > 0 && ins) || (getBalance(node.left) < 0 && !ins))
                node.left = leftRotation(node.left);
            return rightRotation(node);

        } else if (balance < -1) {
            if ((data.compareTo(node.right.data) < 0 && ins) || (getBalance(node.right) > 0 && !ins))
                node.right = rightRotation(node.right);
            return leftRotation(node);
        }
        return node;
    }

    @Override
    public Node<T> leftRotation(Node<T> node) {
        Node<T> newRoot = node.right;
        Node<T> TMP = newRoot.left;

        newRoot.left = node;
        node.right = TMP;

        updateHeight(node);
        updateHeight(newRoot);

        return newRoot;
    }

    @Override
    public Node<T> rightRotation(Node<T> node) {
        Node<T> newRoot = node.left;
        Node<T> TMP = newRoot.right;

        newRoot.right = node;
        node.left = TMP;

        updateHeight(node);
        updateHeight(newRoot);

        return newRoot;
    }

    /**
     * Get the height of a node
     * 
     * @param node node to get height
     * @return height of the node
     */
    public int getHeight(Node<T> node) {
        return node == null ? 0 : node.height;
    }

    /**
     * Update the height of a node
     * 
     * @param node node to update height
     */
    public void updateHeight(Node<T> node) {
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }
}