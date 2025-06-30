package com.proyecto.estructuras.trees;

import java.util.NoSuchElementException;

public class CBT<T extends Comparable<T>> extends ArrayBasedTree<T>{

    /**
     * Constructor for initializing the tree with a specified capacity.
     * @param capacity the maximum capacity of the tree
     */
    public CBT(int capacity) {
        super(capacity);
    }

    /**
     * Constructor for initializing the tree with a capacity of 7.
     */
    public CBT(){}

    @Override
    public void insert(T element) {
        if (isFull())
            resize();
        tree[size++] = element;
    }

    @Override
    public boolean search(T element) {
        for(int i = 0; i < capacity; i++)
            if(tree[i].equals(element))
                return true;
        return false;
    }

    /**
     * Gets the index of an element in the tree.
     * @param element the element to find the index of
     * @return the index of the element
     * @throws NoSuchElementException if the element is not found
     */
    public int getIndex(T element) {
        for(int i = 0; i < capacity; i++)
            if(tree[i].equals(element))
                return i;
        return -1;
    }

    @Override
    public void remove(T element) {
        for(int i = 0; i < size; i++)
            if(tree[i].equals(element)){
                tree[i] = tree[--size];   // como no está organizado, se puede hacer xd
                return;
            }
        System.out.println(element + " Not found");
    }

    /**
     * Extracts the last inserted element from the tree.
     * @return the last inserted element in the tree
     */
    public T extractLast(){
        if(isEmpty())
            throw new NoSuchElementException("Empty Tree");
        return tree[--size];
    }

    /**
     * Gets the parent index of a given node.
     * @param i the index of the node
     * @return the index of the parent node
     */
    public int parent(int i){
        return (i-1)/2; // trunca automaticamente el valor :D
    }

    /**
     * Gets the index of the left child node.
     * @param i the index of the node
     * @return the index of the left child node
     */
    public int leftChild(int i){
        return 2 * i + 1;
    }

    /**
     * Gets the index of the right child node.
     * @param i the index of the node
     * @return the index of the right child node
     */
    public int rightChild(int i){
        return 2 * i + 2;
    }

    @Override
    public void preOrder() {
        System.out.println("Tree is: ");
        if (size != 0)
            traversalRec(0, 1);
        else
            System.out.println("Empty");
        System.out.println("Fin \n");
    }

    @Override
    public void inOrder() {
        System.out.println("Tree is: ");
        if (size != 0)
            traversalRec(0, 2);
        else
            System.out.println("Empty");
        System.out.println("Fin \n");
    }

    @Override
    public void postOrder() {
        System.out.println("Tree is: ");
        if (size != 0)
            traversalRec(0, 3);
        else
            System.out.println("Empty");
        System.out.println("Fin \n");
    }
    
    private void traversalRec(int i, int type){
        if (i >= capacity || tree[i] == null) return;

        int leftChild = leftChild(i);
        int rightChild = rightChild(i);

        if (type == 1)
            System.out.println(tree[i]);
        if (leftChild < capacity && tree[leftChild] != null)
            traversalRec(leftChild, type);  // hijo izquierdo
        if (type == 2)
            System.out.println(tree[i]);
        if (rightChild < capacity && tree[rightChild] != null)
            traversalRec(rightChild, type);  // hijo derecho
        if (type == 3)
            System.out.println(tree[i]);        
    }

    @Override
    public void printTree() {
        System.out.println("\n=== Horizontal tree ===\n");
        if (tree[0] == null) {
            System.out.println("(vacío)");
            return;
        }
        printTreeRec(0, "", true, false);
    }

    private void printTreeRec(int i, String prefix, boolean isRoot, boolean isLeft) {
        if (i >= size || tree[i] == null) return;
    
        int leftChild = leftChild(i);
        int rightChild = rightChild(i);
        
        // Primero procesamos el hijo derecho (va hacia arriba)
        if (rightChild < size && tree[rightChild] != null) {
            printTreeRec(rightChild, 
                prefix + (isRoot ? "" : (isLeft ? "│   " : "    ")), 
                false, false);
        }
        
        // Luego imprimimos el nodo actual
        if (isRoot) {
            System.out.println(prefix + tree[i]);
        } else {
            System.out.println(prefix + (isLeft ? "└── " : "┌── ") + tree[i]);
        }
        
        // Finalmente procesamos el hijo izquierdo (va hacia abajo)
        if (leftChild < size && tree[leftChild] != null) {
            printTreeRec(leftChild, 
                prefix + (isRoot ? "" : (isLeft ? "    " : "│   ")), 
                false, true);
        }
    }
}