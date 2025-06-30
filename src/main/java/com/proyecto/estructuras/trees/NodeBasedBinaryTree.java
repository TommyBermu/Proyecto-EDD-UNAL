package com.proyecto.estructuras.trees;

/**
 * @author Tomas Bermudez
 */
public abstract class NodeBasedBinaryTree<T extends Comparable<T>> implements Tree<T> {
    public static class Node<T extends Comparable<T>> implements Comparable<Node<T>> {
        protected T data;
        protected Node<T> left, right, parent;
        protected int height = 1;     //para el AVL
        protected boolean red = true; // para red-black tree

        /**
         * Node constructor
         * 
         * @param data data to store in the node
         * @param parent parent of the node
         */
        public Node(T data, Node<T> parent) {
            this.data = data;
            this.parent = parent;
        }

        @Override
        public int compareTo(Node<T> o) {
            return this.data.compareTo(o.data);
        }

        @Override
        public String toString() {
            return data.toString() + "(" + (red ? "R" : "B") + ")";
        }
    }

    /**
     * Root of the tree
     */
    protected Node<T> root;

    /**
     * Get the root node of the tree
     * 
     * @return root node
     */
    public Node<T> getRoot() {
        return root;
    }

    @Override
    public abstract void insert(T element);

    @Override
    public abstract boolean search(T element);

    @Override
    public abstract void remove(T element);

    /**
     * Find the next node (in value)in the BST
     * 
     * @param node data to find next node
     * @return next node
     */
    public Node<T> next(Node<T> node) {
        if (node == null)
            return null;

        return node.right != null ? findMin(node.right) : findAncestor(node);
    }

    /**
     * Find the previous node (in value) in the BST
     * 
     * @param node data to find previous node
     * @return previous node
     */
    public Node<T> prev(Node<T> node) {
        if (node == null)
            return null;
        // If right subtree exists, return the minimum of the right subtree
        return node.left != null ? findMax(node.left) : findPredecessor(node);
    }

    /**
     * find the minimum node in the tree rooted at the given node
     * 
     * @param node node to find minimum
     * @return minimum node
     */
    public Node<T> findMin(Node<T> node) {
        while (node != null && node.left != null)
            node = node.left;
        return node;
    }

    /**
     * find the maximum node in the tree rooted at the given node
     * 
     * @param node node to find maximum
     * @return maximum node
     */
    public Node<T> findMax(Node<T> node) {
        while (node != null && node.right != null)
            node = node.right;
        return node;
    }

    /**
     * return the first parent node with a value greater than the given node's value
     * 
     * @param node node to find ancestor
     * @return ancestor node
     */
    public Node<T> findAncestor(Node<T> node) {
        if (node.parent != null && node.data.compareTo(node.parent.data) > 0)
            return findAncestor(node.parent);
        return node.parent;
    }

    /**
     * returns the first parent node with a value less than the given node's value
     * 
     * @param node node to find predecessor
     * @return predecessor node
     */
    public Node<T> findPredecessor(Node<T> node) {
        if (node.parent != null && node.data.compareTo(node.parent.data) < 0)
            return findPredecessor(node.parent);
        return node.parent;
    }

    /**
     * PreOrder Traversal
     */
    @Override
    public void preOrder() { // InOrder
        System.out.println("Tree is: ");
        if (root != null)
            traversalRec(root, 1);
        else
            System.out.println("Empty");
        System.out.println("Fin \n");
    }

    /**
     * InOrder Traversal
     */
    @Override
    public void inOrder() { // InOrder
        System.out.println("Tree is: ");
        if (root != null)
            traversalRec(root, 2);
        else
            System.out.println("Empty");
        System.out.println("Fin \n");
    }

    /**
     * PostOrder Traversal
     */
    @Override
    public void postOrder() { // InOrder
        System.out.println("Tree is: ");
        if (root != null)
            traversalRec(root, 3);
        else
            System.out.println("Empty");
        System.out.println("Fin \n");
    }

    /**
     * Traversal BST
     * 
     * @param node node to traverse
     * @param type traversal type (1: PreOrder, 2: InOrder, 3: PostOrder)
     */
    private void traversalRec(Node<T> node, int type) {
        if (type == 1)
            System.out.println(node.data);
        if (node.left != null)
            traversalRec(node.left, type);
        if (type == 2)
            System.out.println(node.data);
        if (node.right != null)
            traversalRec(node.right, type);
        if (type == 3)
            System.out.println(node.data);
    }

    @Override
    public void printTree() {
        System.out.println("\n=== Horizontal tree ===\n");
        if (root == null) {
            System.out.println("(vacío)");
            return;
        }
        printTreeRec(root, "", true, false);
    }

    /**
     * The recursive method to print the tree
     */
    private void printTreeRec(Node<T> node, String prefix, boolean isRoot, boolean isLeft) {
        if (node == null) return;
        
        // Primero procesamos el hijo derecho (va hacia arriba)
        if (node.right != null) {
            printTreeRec(node.right, 
                prefix + (isRoot ? "" : (isLeft ? "│   " : "    ")), 
                false, false);
        }
        
        // Luego imprimimos el nodo actual
        if (isRoot) {
            System.out.println(prefix + node);
        } else {
            System.out.println(prefix + (isLeft ? "└── " : "┌── ") + node);
        }
        
        // Finalmente procesamos el hijo izquierdo (va hacia abajo)
        if (node.left != null) {
            printTreeRec(node.left, 
                prefix + (isRoot ? "" : (isLeft ? "    " : "│   ")), 
                false, true);
        }
    }
}
