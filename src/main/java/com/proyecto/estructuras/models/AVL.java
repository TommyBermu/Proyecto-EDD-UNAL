package com.proyecto.estructuras.models;

import java.util.ArrayList;
import java.util.List;

class AVL<T extends Comparable<T>> {

    static class Node<T> {
        T key;
        Node<T> left;
        Node<T> right;
        int height;
        public Node (T k){
            this.key = k;
            left = null;
            right = null;
            height = 1;
        }
    }

    // Atributo raíz del árbol
    private Node<T> root;

    // Insertar un valor en el árbol
    public void insert(T key) {
        root = insertRecursivo(root, key);
    }

    // Eliminar un valor del árbol
    public void delete(T key) {
        root = deleteRecursivo(root, key);
    }

    //Retorna una lista ordenada de los valores
    public List<T> getInOrder(){
        List<T> listA = new ArrayList<>();
        inOrder(root, listA);
        return listA;
    }

    //-------- METODOS PRIVADOS -----------

    //Retorna la altura de un nodo
    private int height(Node<T> N){
        return N == null ? 0 : N.height;
    }

    //Realiza la rotación a la derecha y retorna el nodo raiz de la rotación
    private Node<T> rightRotate(Node<T> y){
        Node<T> x = y.left;
        Node<T> B = x.right;
        x.right = y;
        y.left = B;

        //Update heights
        y.height = 1 + Math.max(height(y.left), height(y.right));
        x.height = 1 + Math.max(height(x.left), height(x.right));
        return x;
    }

    //Realiza la rotación a la izquierda y retorna el nodo raiz de la rotación
    private Node<T> leftRotate(Node<T> x){ //x
        Node<T> y = x.right;
        Node<T> B = y.left;
        y.left = x;
        x.right = B;

        //Update heights
        y.height = 1 + Math.max(height(y.left), height(y.right));
        x.height = 1 + Math.max(height(x.left), height(x.right));
        return y;
    }

    //Retorna el factor de balance de un nodo
    private int getBalance(Node<T> N){
        return N == null ? 0 : height(N.left) - height(N.right);
    }

    private Node<T> insertRecursivo(Node<T> node, T key) {

        // Perform the normal BST insertion
        if (node == null)
            return new Node<>(key);

        if (key.compareTo(node.key) < 0) //key < node.key
            node.left = insertRecursivo(node.left, key);
        else // key >= node.key -- incluye valores repetidos
            node.right = insertRecursivo(node.right, key);

        // Update height of this ancestor node
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // Get the balance factor of this ancestor node
        int balance = getBalance(node);
        // Return the (unchanged) node pointer, es la misma recursión del BST, retorna la raiz del arbol con las rotaciones realizadas
        return performRotations(balance, node);
    }

    //Encuentra el nodo que se encuentra más a la izquierda
    private Node<T> minValueNode(Node<T> nodo){
        Node<T> actual = nodo;
        while (actual.left != null) {
            actual = actual.left;
        }
        return actual;
    }

    // Recursive function to delete a node with
    // given key from subtree with given root.
    // It returns root of the modified subtree.
    private Node<T> deleteRecursivo(Node<T> root, T key) {
        // STEP 1: PERFORM STANDARD BST DELETE
        if (root == null)
            return root;

        if (key.compareTo(root.key) < 0) // key < root.key
            root.left = deleteRecursivo(root.left, key);
        else if (key.compareTo(root.key) > 0) // key > root.key
            root.right = deleteRecursivo(root.right, key);
        else {
            // Case 0 and 1
            if ((root.left == null) || (root.right == null)) {
                Node<T> temp = root.left != null ? root.left : root.right;

                // No child case
                if (temp == null) {
                    temp = root;
                    root = null;
                } else // One child case
                    root = temp; // Copy the contents of the non-empty child
            } else {
                Node<T> temp = minValueNode(root.right);
                root.key = temp.key;
                root.right = deleteRecursivo(root.right, temp.key);
            }
        }

        // If the tree had only one node then return
        if (root == null)
            return root;

        // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE
        root.height = Math.max(height(root.left), height(root.right)) + 1;

        int balance = getBalance(root);
        // If this node becomes unbalanced, then there are 4 cases
        return performRotations(balance, root);
    }

    //Evalua cual de los 4 casos de rotación hay que realizar
    private Node<T> performRotations(int balance, Node<T> root){
        // Left Left Case
        if (balance > 1 && getBalance(root.left) >= 0){
            return rightRotate(root);
        }
        // Left Right Case
        else if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }
        // Right Right Case
        else if (balance < -1 && getBalance(root.right) <= 0)
            return leftRotate(root);
            // Right Left Case
        else if (balance < -1 && getBalance(root.right) > 0){
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }
        return root;
    }

    //Recorre el arbol de menor a mayor de forma recursiva y añade el valor de cada nodo en una lista
    private void inOrder(Node<T> root, List<T> lista) {
        if (root != null) {
            inOrder(root.left,  lista);
            lista.add(root.key);
            inOrder(root.right, lista);
        }
    }

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
