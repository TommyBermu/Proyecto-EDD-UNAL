package com.proyecto.estructuras.trees;
import com.proyecto.estructuras.trees.NodeBasedBinaryTree.Node;

/**
 * @author Tomas Bermudez
 */
public interface SelfBalanced<T extends Comparable<T>> {
    /**
     * Rebalances the tree at the given node after an operation.
     * This method checks the balance and performs the necessary rotations.
     * @param node the root of the subtree to rebalance
     * @param data the data that was inserted, used to determine the rotation direction
     * @param ins tells if it is an isertion or a deletion. Useful if the conditionals change but the rotations don't
     */
    abstract Node<T> rebalance(Node<T> node, T data, boolean ins);

    /**
     * Performs a left rotation on the given node.
     * This is used to maintain the balance of the tree.
     * @param node the root of the subtree to rotate
     * @return the new root of the subtree
     */
    abstract Node<T> leftRotation(Node<T> node);

    /**
     * Performs a right rotation on the given node.
     * This is used to maintain the balance of the tree.
     * @param node the root of the subtree to rotate
     * @return the new root of the subtree
     */
    abstract Node<T> rightRotation(Node<T> node);
}
