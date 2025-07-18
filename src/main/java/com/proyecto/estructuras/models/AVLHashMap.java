package com.proyecto.estructuras.models;

import java.util.ArrayList;
import java.util.List;

public class AVLHashMap<K, V extends Comparable<V>> {
    public static class Node<K, V extends Comparable<V>> implements Comparable<Node<K, V>> {
        protected V data;
        // para el avl
        protected Node<K, V> left, right, parent;
        protected int height = 1;
        // para el hashmap
        protected K key;
        protected Node<K, V> next;
        
        /**
         * Node constructor
         * 
         * @param key key to store in the node
         * @param data data to store in the node
         * @param parent parent of the node
         * @param next next node in the linked list
         */
        public Node(K key, V data, Node<K, V> parent, Node<K, V> next) {
            this.key = key;
            this.data = data;
            this.parent = parent;
            this.next = next;
        }

        @Override
        public int compareTo(Node<K, V> o) {
            return this.data.compareTo(o.data);
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }

    private Node<K, V> root;
    private Node<K, V>[] buckets;
    private int size, capacity;
    private static final float LOAD_FACTOR = 0.75f;

    @SuppressWarnings("unchecked")
    public AVLHashMap(){
        this.capacity = 16;
        this.size = 0;
        this.buckets = new Node[capacity];
    }

    @SuppressWarnings("unchecked")
    public AVLHashMap(int capacity){
        if (capacity <= 0) throw new IllegalArgumentException("Capacity must be positive");
        this.capacity = capacity;
        this.size = 0;
        this.buckets = new Node[capacity];
    }

    /**
     * Retrieves the value associated with the specified key.
     * 
     * @param key the key whose associated value is to be returned
     * @return the value associated with the key, or null if the key does not exist
     */
    public V get(K key) {
        Node<K, V> node = getNode(key);
        return node == null ? null : node.data;
    }

    /**
     * Retrieves the node associated with the specified key.
     * 
     * @param key the key whose associated value is to be returned
     * @return the node associated with the key, or null if the key does not exist
     */
    public Node<K, V> getNode(K key) {
        int idx = hash(key.hashCode());
        Node<K, V> curr = buckets[idx];
        while (curr != null){
            if (curr.key.equals(key)){
                return curr;
            }
            curr = curr.next;
        }
        return null;
    }

    /**
     * Inserts a key-value pair into the structure.
     * 
     * @param key the key to insert
     * @param value the value to insert
     * @return true if the insertion was successful, false if the key already exists
     */
    public boolean insert(K key, V value) {
        Node<K, V> curr = buckets[hash(key.hashCode())];
        while (curr != null){
            if (curr.key.equals(key)){
                return false;
            }
            curr = curr.next;
        }
        root = insertRec(root, value, null, key);
        size++;
        if (size >= capacity * LOAD_FACTOR)
            resize();
        return true;
    }

    /**
     * Insert a new node into the tree
     * 
     * @param node node to which we want to make a child
     * @param data data to create the child
     * @return updated node
     */
    protected Node<K, V> insertRec(Node<K, V> node, V data, Node<K, V> parent, K key) {
        if (node == null)
            return rebalance(buckets[hash(key.hashCode())] = 
            new Node<>(key, data, parent, buckets[hash(key.hashCode())]), data, true);

        if (node.data.compareTo(data) > 0)
            node.left = insertRec(node.left, data, node, key);

        else if (node.data.compareTo(data) < 0)
            node.right = insertRec(node.right, data, node, key);

        else {
            System.out.println("El valor " + data + " ya existe en el árbol");
            return node;
        }
        return rebalance(node, data, true);
    }
    
    /**
     * Removes a key from the structure, both from the hash map and the AVL tree.
     * 
     * @param key key to remove
     */
    public void remove(K key) {
        Node<K, V> node = getNode(key);

        if (node == null){
            System.out.println("Item not in database and not removed");
            return;
        }

        removeFromHashMap(key);
        removeFromAVL(node);
        size--;
    }

    /**
     * Removes a key from the hash map
     * 
     * @param key the key to remove
     */
    private void removeFromHashMap(K key) {
        int idx = hash(key.hashCode());
        Node<K, V> curr = buckets[idx];
        Node<K, V> prev = null;
        
        while (curr != null) {
            if (curr.key.equals(key)) {
                if (prev == null) {
                    buckets[idx] = curr.next; // Es el primer nodo del bucket
                } else {
                    prev.next = curr.next; // Es un nodo en el medio o final
                }
                return;
            }
            prev = curr;
            curr = curr.next;
        }
    }

    /**
     * Removes a node from the AVL tree.
     * 
     * @param nodeToRemove the node to remove
     */
    private void removeFromAVL(Node<K, V> nodeToRemove) {
        // Caso 1: Sin hijos (hoja)
        if (nodeToRemove.left == null && nodeToRemove.right == null) {
            if (nodeToRemove.parent == null) {
                root = null;
            } else {
                Node<K, V> parent = nodeToRemove.parent;
                // Desconectar del padre
                if (parent.left != null && parent.left.equals(nodeToRemove)) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
                rebalanceUp(parent, nodeToRemove.data);
            }
        }
        
        // Caso 2: Solo hijo derecho
        else if (nodeToRemove.left == null) {
            nodeToRemove.right.parent = nodeToRemove.parent;
            
            if (nodeToRemove.parent == null) {
                root = nodeToRemove.right;
            } else {
                // Conectar el hijo con el abuelo
                if (nodeToRemove.parent.left != null && nodeToRemove.parent.left.equals(nodeToRemove)) {
                    nodeToRemove.parent.left = nodeToRemove.right;
                } else {
                    nodeToRemove.parent.right = nodeToRemove.right;
                }
                rebalanceUp(nodeToRemove.parent, nodeToRemove.data);
            }
        }
        
        // Caso 3: Solo hijo izquierdo
        else if (nodeToRemove.right == null) {
            nodeToRemove.left.parent = nodeToRemove.parent;
            
            if (nodeToRemove.parent == null) {
                root = nodeToRemove.left;
            } else {
                // Conectar el hijo con el abuelo
                if (nodeToRemove.parent.left != null && nodeToRemove.parent.left.equals(nodeToRemove)) {
                    nodeToRemove.parent.left = nodeToRemove.left;
                } else {
                    nodeToRemove.parent.right = nodeToRemove.left;
                }
                rebalanceUp(nodeToRemove.parent, nodeToRemove.data);
            }
        }
        
        // Caso 4: Dos hijos - reemplazar con sucesor
        else {
            Node<K, V> successor = findMin(nodeToRemove.right);
            nodeToRemove.data = successor.data;
            removeFromAVL(successor);
        }
    }

    /**
     * Rebalance the tree upwards after a removal.
     * 
     * @param node the node to start rebalancing from
     * @param removedData the data that was removed, used to determine the rotation direction
     */
    private void rebalanceUp(Node<K, V> node, V removedData) {
        while (node != null) {
            Node<K, V> newRoot = rebalance(node, removedData, false);
            
            // Si el nodo cambió después del rebalance, actualizar las referencias del padre
            if (newRoot != node) {
                if (newRoot.parent == null) {
                    // Se convirtió en la nueva raíz
                    root = newRoot;
                } else {
                    // Actualizar la referencia del padre
                    if (newRoot.parent.left == node) {
                        newRoot.parent.left = newRoot;
                    } else {
                        newRoot.parent.right = newRoot;
                    }
                }
            }
            node = newRoot.parent;
        }
    }

    /**
     * Hashes an integer key
     * 
     * @param x the key to hash
     * @return the hashed key
     */
    public int hash(int x) { // como la key que recibe es un entero, el hashcode de un entero es el mismo xd
        x = ((x >>> 16) ^ x) * 0x45d9f3b;
        x = ((x >>> 16) ^ x) * 0x45d9f3b;
        x = (x >>> 16) ^ x;
        return x & (capacity -1);
    }

    /**
     * Resizes the hash map when the load factor exceeds the threshold.
     */
    @SuppressWarnings("unchecked")
    private void resize(){
        Node<K, V>[] oldBuckets = buckets;
        int oldCapacity = capacity;
        
        this.capacity = oldCapacity * 2;
        buckets = new Node[capacity];

        size = 0;
        
        // Rehash todos los elementos existentes
        for (int i = 0; i < oldCapacity; i++) {
            Node<K, V> curr = oldBuckets[i];
            
            while (curr != null) {
                Node<K, V> next = curr.next;
                
                int newIdx = hash(curr.key.hashCode());
                curr.next = buckets[newIdx];
                buckets[newIdx] = curr;
                size++;
                
                curr = next;
            }
        }
    }

    /**
     * Replaces the value for the given key.
     * 
     * @param key the key to replace
     * @param value the new value
     * @return the old value, or null if the key was not found
     */
    public V replace(K key, V value) {
        Node<K, V> node = getNode(key);
        if (node == null) {
            return null;
        }
        
        V oldValue = node.data;
        
        if (!oldValue.equals(value)) {
            remove(key);
            insert(key, value);
        }
        
        return oldValue;
    }

    /**
     * Checks if the map contains the given key.
     * 
     * @param key the key to check
     * @return true if the key is found, false otherwise
     */
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * Returns the size of the hash map.
     * 
     * @return the number of key-value pairs in the map
     */
    public int size() {
        return this.size;
    }

    /**
     * Clears the hash map and the AVL tree.
     */
    @SuppressWarnings("unchecked")
    public void clear() {
        this.root = null;
        this.buckets = new Node[capacity];
    }

    /**
     * Calculates the height of the given node.
     * 
     * @param node the node to check
     * @return the height of the node
     */
    public int getBalance(Node<K, V> node) {
        return node != null ? getHeight(node.left) - getHeight(node.right) : 0;
    }

    /**
     * Rebalances the tree at the given node after an operation.
     * This method checks the balance and performs the necessary rotations.
     * @param node the root of the subtree to rebalance
     * @param data the data that was inserted, used to determine the rotation direction
     * @param ins tells if it is an insertion or a deletion. Useful if the conditionals change but the rotations don't
     */
    public Node<K, V> rebalance(Node<K, V> node, V data, boolean ins) {
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

    /**
     * Performs a left rotation on the given node.
     * This is used to maintain the balance of the tree.
     * @param node the root of the subtree to rotate
     * @return the new root of the subtree
     */
    public Node<K, V> leftRotation(Node<K, V> node) {
        Node<K, V> newRoot = node.right;
        Node<K, V> TMP = newRoot.left;

        newRoot.left = node;
        node.right = TMP;

        newRoot.parent = node.parent;
        node.parent = newRoot;
        if (TMP != null) {
            TMP.parent = node;
        }

        updateHeight(node);
        updateHeight(newRoot);

        return newRoot;
    }

    /**
     * Performs a right rotation on the given node.
     * This is used to maintain the balance of the tree.
     * @param node the root of the subtree to rotate
     * @return the new root of the subtree
     */
    public Node<K, V> rightRotation(Node<K, V> node) {
        Node<K, V> newRoot = node.left;
        Node<K, V> TMP = newRoot.right;

        newRoot.right = node;
        node.left = TMP;

        newRoot.parent = node.parent;
        node.parent = newRoot;
        if (TMP != null) {
            TMP.parent = node;
        }

        updateHeight(node);
        updateHeight(newRoot);

        return newRoot;
    }

    /**
     * find the minimum node in the tree rooted at the given node
     * 
     * @param node node to find minimum
     * @return minimum node
     */
    public Node<K, V> findMin(Node<K, V> node) {
        while (node != null && node.left != null)
            node = node.left;
        return node;
    }

    /**
     * Get the height of a node
     * 
     * @param node node to get height
     * @return height of the node
     */
    public int getHeight(Node<K, V> node) {
        return node == null ? 0 : node.height;
    }

    /**
     * Update the height of a node
     * 
     * @param node node to update height
     */
    public void updateHeight(Node<K, V> node) {
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    /**
     * Returns a list of all values (V) in the AVL tree in in-order.
     * This is useful for serialization.
     */
    public List<V> getAllValues() {
        List<V> values = new ArrayList<>();
        collectValuesInOrder(root, values);
        return values;
    }

    private void collectValuesInOrder(Node<K, V> node, List<V> values) {
        if (node == null) {
            return;
        }
        collectValuesInOrder(node.left, values);
        values.add(node.data);
        collectValuesInOrder(node.right, values);
    }
}