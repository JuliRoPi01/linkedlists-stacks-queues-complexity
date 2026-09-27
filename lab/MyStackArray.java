package lab;

public class MyStackArray<T> implements MyStack<T>{
    private T[] array;
    private int top;
    private int capacity;

    @SuppressWarnings ("unchecked")
    public MyStackArray(int initialCapacity){
        capacity = initialCapacity;
        array = (T[]) new Object[capacity];
        top = -1;
    }

    public void push(T data){
        top++;
        if(top == capacity-1) resize();
        array[top] = data;
    }

    public T pop(){
        if(isEmpty()) throw new Error("Stack vacia");
        T data = array[top];
        array[top] = null;
        top--;
        return data;
    }

    public T peek(){
        if(isEmpty()) throw new Error("Stack vacia");
        return array[top];
    }

    public boolean isEmpty(){
        return top == -1;
    }

    public int size(){
        return top+1;
    }

    public void delete(T data){
        int pos = 0;
        while(pos <= top && !array[pos].equals(data)){
            pos++;
        }
        if(pos > top) throw new Error("Elemento no encontrado");
        while(pos < top){
            array[pos] = array[pos+1];
            pos++;
        }
        array[top] = null;
        top--;

    }

    @SuppressWarnings ("unchecked")
    public void resize(){
        capacity *= 2;
        T[] newArray = (T[]) new Object[capacity];
        for(int i = 0; i < top; i++){
            newArray[i] = array[i];
        }
        array = newArray;
    }

    public void print(){
        for(int i = 0; i <= top; i++){
            System.out.print(array[i]  + " ");
        }
        System.out.println();
    }
}
