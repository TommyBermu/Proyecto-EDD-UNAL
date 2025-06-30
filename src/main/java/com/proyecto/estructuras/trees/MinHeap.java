package com.proyecto.estructuras.trees;

/**
 * @author Tomas Bermudez
 */
public class MinHeap<T extends Comparable<T>> extends CBT<T>{
    T minPriority;
    public MinHeap(int capacity, T minPriority) {
        super(capacity);
        this.minPriority = minPriority;
    }

    public MinHeap(T minPriority) {
        super();
        this.minPriority = minPriority;
    }

    public MinHeap(){
        super();
    }

    public void shiftUp(int i){
        while (i > 0 && tree[parent(i)].compareTo(tree[i]) > 0){
            swap(parent(i), i);
            i = parent(i);
        }
    }    
    
    public void shiftDown(int i){
        int maxIndex = i;
        int l = leftChild(i);
        int r = rightChild(i);

        if (l < size && tree[l].compareTo(tree[maxIndex]) < 0)
            maxIndex = l;

        if (r < size && tree[r].compareTo(tree[maxIndex]) < 0)
            maxIndex = r;

        if (i != maxIndex){
            swap(i, maxIndex);
            shiftDown(maxIndex);
        }
    }

    private void swap(int i, int j) {
        T temp = tree[i];
        tree[i] = tree[j];
        tree[j] = temp;
    }

    @Override
    public void insert(T element){
        super.insert(element);
        shiftUp(size-1);
    }

    public T extractMin(){
        T result = tree[0];
        tree[0] = tree[--size];
        shiftDown(0);
        return result;
    }

    @Override
    public void remove(T element){
        delete(getIndex(element));
    }

    public T delete(int i) {
        setValue(i, minPriority);
        shiftUp(i);
        return extractMin();
    }

    public void changePriority(int i, T p){
        T old = tree[i];
        tree[i] = p;
        if(p.compareTo(old) < 0)
            shiftUp(i);
        else
            shiftDown(i);
    }

    public static <T extends Comparable<T>> void heapSort(T[] array){
        // TODO aca es n*log(n) + n*log(n), en las diapos hay una implementacion que es n + n*log(n), o sea mas eficiente xd
        MinHeap<T> mHeap = new MinHeap<>();
        for (T item: array)
            mHeap.insert(item);

        for(int i = array.length - 1; i >= 0; i--)
            array[i] = mHeap.extractMin();
    }
}
