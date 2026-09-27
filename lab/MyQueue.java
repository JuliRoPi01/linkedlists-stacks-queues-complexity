package lab;

public interface MyQueue <T>{
    void enqueue(T data);
    T dequeue();
    T front();
    boolean isEmpty();
    int size();
    void delete(T data);
    void print();
}
