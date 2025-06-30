package com.proyecto.estructuras.trees;

/**
 * @author Tomas Bermudez
 */
public interface Tree<T>{
    /**
     * Insert a new node into the tree
     * 
     * @param data data to insert
     */
    public void insert(T data);

    /**
     * Remove a node from the tree
     * 
     * @param data data to remove
     */
    public void remove(T data);

    /**
     * Searches for an element in the tree.
     *
     * @param data the element to search for
     * @return true if the element is found, false otherwise
     */
    public boolean search(T data);

    /**
     * Traverses the tree in pre-order.
     */
    public void preOrder();

    /**
     * Traverses the tree in in-order.
     */
    public void inOrder();

    /**
     * Traverses the tree in post-order.
     */
    public void postOrder();

    /**
     * This method prints the tree structure to the console.
     */
    public void printTree();
}
