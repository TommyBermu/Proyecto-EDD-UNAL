package com.proyecto.estructuras.models;

public class HashMap<K, V> {
    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;

        public Node(K key, V value, Node<K, V> next){
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private Node<K, V>[] buckets;
    private int size, capacity;
    private static final float LOAD_FACTOR = 0.75f;

    @SuppressWarnings("unchecked")
    public HashMap(){
        this.capacity = 16;
        this.size = 0;
        this.buckets = new Node[capacity];
    }

    @SuppressWarnings("unchecked")
    public HashMap(int capacity){
        if (capacity <= 0) throw new IllegalArgumentException("Capacity must be positive");
        this.capacity = capacity;
        this.size = 0;
        this.buckets = new Node[capacity];
    }

    private int hash(K key){
        return (key.hashCode() & 0x7fffffff) % capacity;
    }

    public boolean add(K key, V value) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        int idx = hash(key);
        Node<K, V> curr = buckets[idx];
        while (curr != null){
            if (curr.key.equals(key)){
                return false;
            }
            curr = curr.next;
        }
        buckets[idx] = new Node<>(key, value, buckets[idx]);
        size++;
        if ((float) size/capacity >= LOAD_FACTOR)
            resize();
        return true;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
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

                int newIdx = hash(curr.key);
                curr.next = buckets[newIdx];
                buckets[newIdx] = curr;
                size++;

                curr = next;
            }
        }
    }

    public V replace(K key, V value) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        int idx = hash(key);
        Node<K, V> curr = buckets[idx];
        while (curr != null){
            if (curr.key.equals(key)){
                V val = curr.value;
                curr.value = value;
                return val;
            }
            curr = curr.next;
        }
        return null;
    }

    public V get(K key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        int idx = hash(key);
        Node<K, V> curr = buckets[idx];
        while (curr != null){
            if (curr.key.equals(key)){
                return curr.value;
            }
            curr = curr.next;
        }
        return null;
    }

    public V remove(K key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        int idx = hash(key);
        Node<K, V> curr = buckets[idx];

        // Si es el primer nodo
        if (curr != null && curr.key.equals(key)) {
            buckets[idx] = curr.next;
            size--;
            return curr.value;
        }

        // Para el resto de nodos
        while (curr != null && curr.next != null) {
            if (curr.next.key.equals(key)) {
                V ret = curr.next.value;
                curr.next = curr.next.next;
                size--;
                return ret;
            }
            curr = curr.next;
        }
        return null;
    }

    public boolean containsKey(K key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        int idx = hash(key);
        Node<K, V> curr = buckets[idx];
        while (curr != null) {
            if (curr.key.equals(key)) {
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    public int size() {
        return this.size;
    }

    @SuppressWarnings("unchecked")
    public void clear() {
        this.size = 0;
        this.buckets = new Node[capacity];
    }
}