package com.proyecto.estructuras.trees;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tomas Bermudez
 */
public class BST<T extends Comparable<T>> extends NodeBasedBinaryTree<T> {
    /**
     * Constructor (Empty)
     */
    public BST() {
    }

    /**
     * Constructor with data
     * 
     * @param data data to insert as root
     */
    public BST(T data) {
        insert(data);
    }

    @Override
    public void insert(T data) {
        root = insertRec(root, data, null);
    }

    /**
     * Insert a new node into the tree
     * 
     * @param node node to which we want to make a child
     * @param data data to create the child
     * @return updated node
     */
    protected Node<T> insertRec(Node<T> node, T data, Node<T> parent) {
        if (node == null)
            return postInsert(new Node<T>(data, parent), data);

        if (node.data.compareTo(data) > 0)
            node.left = insertRec(node.left, data, node);

        else if (node.data.compareTo(data) < 0)
            node.right = insertRec(node.right, data, node);

        else {
            System.out.println("El valor " + data.toString() + " ya existe en el arbol");
            return node;
        }
        return postInsert(node, data);
    }

    /**
     * Post-insert hook method to allow for additional operations after insertion
     * This method can be overridden in subclasses to perform additional operations after a node has been inserted.
     * 
     * @param node  node that was just inserted
     * @param data  data that was inserted
     * @return updated node
     */
    protected Node<T> postInsert(Node<T> node, T data){
        System.out.println("post insert bst");
        return node;
    }

    @Override
    public void remove(T data) {
        root = removeRec(root, data);
    }

    /**
     * Remove a node from the tree
     * 
     * @param node node to remove
     * @param data data to remove
     * @return updated node
     */
    protected Node<T> removeRec(Node<T> node, T data) {
        if (node == null) {
            System.out.println("Item not in Tree and not removed");
            return node;
        }

        if (node.data.compareTo(data) > 0)
            node.left = removeRec(node.left, data);

        else if (node.data.compareTo(data) < 0)
            node.right = removeRec(node.right, data);

        else if (node.left == null && node.right == null) { // no children (leaf)
            return null;

        } else if (node.left == null) { // if only has right child
            node.right.parent = node.parent;
            node = node.right;

        } else if (node.right == null) { // if only has left child
            node.left.parent = node.parent;
            node = node.left;

        } else { // if has both children
            node.data = findMin(node.right).data;
            node.right = removeRec(node.right, node.data);

            // Alternatively, we could use the maximum of the left subtree
            // node.data = findMax(node.left).data;
            // node.left = removeRec(node.left, node.data);
        }
        return postDelete(node, data);
    }

    /**
     * Post-delete hook method to allow for additional operations after deletion
     * This method can be overridden in subclasses to perform additional operations after a node has been deleted.
     *
     * @param node  node that was just deleted
     * @param data  data that was deleted
     * @return updated node
     */
    protected Node<T> postDelete(Node<T> node, T data){
        return node;
    }

    @Override
    public boolean search(T data) {
        return searchRec(root, data);
    }

    /**
     * Search for an element in the tree
     * 
     * @param node node to search
     * @param data element to search
     * @return true if found, false otherwise
     */
    private boolean searchRec(Node<T> node, T data) {
        if (node == null)
            return false;

        if (data.compareTo(node.data) == 0)
            return true;

        if (data.compareTo(node.data) < 0)
            return searchRec(node.left, data);
        else
            return searchRec(node.right, data);
    }

    /**
     * Fetch a node by its data
     * 
     * @param data data to fetch
     * @return fetched node
     */
    public Node<T> fetch(T data) {
        return fetchRec(root, data);
    }

    /**
     * Fetch a node by its data
     * 
     * @param node node to search
     * @param data data to fetch
     * @return fetched node
     */
    private Node<T> fetchRec(Node<T> node, T data) {
        if (node == null || data.compareTo(node.data) == 0)
            return node;

        if (data.compareTo(node.data) < 0)
            return fetchRec(node.left, data);
        else
            return fetchRec(node.right, data);
    }

    /**
     * Nearest neighbors search
     * 
     * @param data data to search nearest neighbors
     */
    public void nearestNeighbors(T data) {
        /*
         * TODO ver como hacer esto xd
         * Node<T> predecesor, sucesor;
         * Node<T> node = root;
         * 
         * while (node != null) {
         * if (data.compareTo(node.data) < 0) {
         * sucesor = node;
         * node = node.left;
         * } else if (data.compareTo(node.data) > 0) {
         * predecesor = node;
         * node = node.right;
         * } else {
         * predecesor = prev(node);
         * sucesor = next(node);
         * break;
         * }
         * }
         */
    }

    /**
     * Range search
     * 
     * @param min min value
     * @param max max value
     * @return list of nodes within the range
     */
    public List<Node<T>> rangeSearch(T min, T max) {
        Node<T> node = findMin(root);
        List<Node<T>> result = new ArrayList<>();

        while (node != null) {
            if (node.data.compareTo(max) > 0)
                break;

            if (node.data.compareTo(min) >= 0)
                result.add(node);

            node = next(node);
        }
        return result;
    }
}