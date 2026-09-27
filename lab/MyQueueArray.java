package lab;

public class MyQueueArray<T> implements MyQueue<T>{
    private T[] array;
    private int front;
    private int size;
    private int capacity;

    @SuppressWarnings ("unchecked")
    public MyQueueArray(int initialCapacity){
        capacity = initialCapacity;
        array = (T[]) new Object[capacity];
        front = 0;
        size = 0;
    }

    public void enqueue(T data){
        if(size == capacity) resize();
        array[(front+size)%capacity] = data;
        size++;
    }

    public T dequeue(){
        if(isEmpty()) throw new Error("Queue vacia");
        T data = array[front];
        array[front] = null;
        front = (front+1)%capacity;
        size--;
        return data;
    }

    public T front(){
        if(isEmpty()) throw new Error("Queue vacia");
        return array[front];
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    public void delete(T data){
        if(array[front].equals(data)){
            array[front] = null;
            front = (front+1)%capacity;
            size--;
            return;
        }
        int pos = front;
        int total = front+size;
        while(pos < total && !array[pos%capacity].equals(data)){
            pos++;
        }
        if(pos >= total) throw new Error("Elemento no encontrado");
        while(pos < total-1){
            array[pos%capacity] = array[(pos+1)%capacity];
            pos++;
        }
        array[pos%capacity] = null;
        size--;
    }

    @SuppressWarnings ("unchecked")
    public void resize(){
        int newCapacity = capacity * 2;
        T[] newArray = (T[]) new Object[newCapacity];
        for(int i = 0; i < size; i++){
            newArray[i] = array[(i+front)%capacity];
        }
        array = newArray;
        front = 0;
        capacity = newCapacity;
    }

    public void print(){
        for(int i = 0; i < size; i++){
            System.out.print(array[front+i] + " ");
        }
        System.out.println();
    }
}