package com.proyecto.estructuras.models;

class Node {
    int key;
    Node left;
    Node right;
    int height;
    public Node (int k){
        this.key = k;
        left = null;
        right = null;
        height = 1;
    }
}

class AVL {
    public static int height(Node N){
        if (N == null){
            return 0;
        }
        return N.height;
    }

    public static Node rightRotate(Node y){
        Node x = y.left;
        Node B = x.right;
        x.right = y;
        y.left = B;

        //Update heights
        y.height = 1 + Math.max(height(y.left), height(y.right));
        x.height = 1 + Math.max(height(x.left), height(x.right));
        return x;
    }

    public static Node leftRotate(Node x){ //x
        Node y = x.right;
        Node B = y.left;
        y.left = x;
        x.right = B;

        //Update heights
        y.height = 1 + Math.max(height(y.left), height(y.right));
        x.height = 1 + Math.max(height(x.left), height(x.right));
        return y;
    }

    public static int getBalance(Node N){
        if (N == null){
            return 0;
        }
        return height(N.left) - height(N.right);
    }

    public static Node insert(Node node, int key) {

        // Perform the normal BST insertion
        if (node == null)
            return new Node(key);

        if (key < node.key)
            node.left = insert(node.left, key);
        else if (key > node.key)
            node.right = insert(node.right, key);
        else // Equal keys are not allowed in BST
            return node;

        // Update height of this ancestor node
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // Get the balance factor of this ancestor node
        int balance = getBalance(node);
        node = performRotations(balance, node);
        // Return the (unchanged) node pointer, es la misma recursión del BST
        return node;
    }


    public static Node minValueNode(Node nodo){
        Node actual = nodo;
        while (actual.left != null) {
            actual = actual.left;
        }
        return actual;
    }

    // Recursive function to delete a node with
    // given key from subtree with given root.
    // It returns root of the modified subtree.
    public static Node delete(Node root, int key) {
        // STEP 1: PERFORM STANDARD BST DELETE
        if (root == null)
            return root;

        if (key < root.key)
            root.left = delete(root.left, key);
        else if (key > root.key)
            root.right = delete(root.right, key);
        else {
            // Case 0 and 1
            if ((root.left == null) || (root.right == null)) {
                Node temp = root.left != null ? root.left : root.right;

                // No child case
                if (temp == null) {
                    temp = root;
                    root = null;
                } else // One child case
                    root = temp; // Copy the contents of the non-empty child
            } else {
                Node temp = minValueNode(root.right);
                root.key = temp.key;
                root.right = delete(root.right, temp.key);
            }
        }

        // If the tree had only one node then return
        if (root == null)
            return root;

        // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE
        root.height = Math.max(height(root.left), height(root.right)) + 1;

        int balance = getBalance(root);
        // If this node becomes unbalanced, then there are 4 cases
        root = performRotations(balance, root);
        return root;
    }

    public static Node performRotations(int balance, Node root){
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
            // (balance < -1 && getBalance(root.right) > 0)
        else if (balance < -1 && getBalance(root.right) > 0){
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }
        return root;
    }

    public static void preOrder(Node root) {
        if (root != null) {
            System.out.print(root.key + " ");
            preOrder(root.left);
            preOrder(root.right);
        }
    }



}
