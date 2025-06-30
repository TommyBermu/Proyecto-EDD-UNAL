package com.proyecto.estructuras.trees;

/**
 * @author Tomas Bermudez
 */
public abstract class ArrayBasedTree<T extends Comparable<T>> implements Tree<T> {
    protected T[] tree;
    protected int size = 0;
    protected int capacity;    
    
    /**
     * Constructor for initializing the tree with a specified capacity.
     * @param capacity the maximum capacity of the tree
     */
    @SuppressWarnings("unchecked")
    public ArrayBasedTree(int capacity) {
        tree = (T[]) new Comparable[capacity];
        this.capacity = capacity;
    }

    /**
     * Constructor for initializing the tree with a capacity of 7.
     */
    @SuppressWarnings("unchecked")
    public ArrayBasedTree() {
        tree = (T[]) new Comparable[7];
        this.capacity = 7;
    }

    /**
     * Gets the current capacity of the tree.
     * @return the capacity of the tree
     */
    public int getCapacity(){
        return this.capacity;
    }

    /**
     * Gets the current size of the tree.
     * @return the size of the tree
     */
    public int getSize(){
        return this.size;
    }    
    
    /**
     * Resizes the tree to increase its capacity.
     * 
     */
    @SuppressWarnings("unchecked")
    public void resize(){
        T[] temp = (T[]) new Comparable[capacity*2+1];
        for (int i = 0; i < capacity; i++)
            temp[i] = tree[i];
        
        this.capacity = capacity*2+1;
        this.tree = temp;
    }

    /**
     * Sets the value of a node in the tree.
     * @param i the index of the node
     * @param data the value to set
     */
    public void setValue(int i, T data){
        if (i >= capacity) return;
        tree[i] = data;
    }

    /**
     * Gets the value of a node in the tree.
     * @param i the index of the node
     * @return the value of the node
     */
    public T getValue(int i){
        return tree[i];
    }

    /**
     * Checks if the tree is empty.
     * @return true if the tree is empty, false otherwise
     */
    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * Checks if the tree is full.
     * @return true if the tree is full, false otherwise
     */
    public boolean isFull(){
        return size == capacity;
    }
}