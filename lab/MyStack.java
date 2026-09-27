package lab;

public interface MyStack <T>{
    void push(T data);
    T pop();
    T peek();
    boolean isEmpty();
    int size();
    void delete(T data);
    void print();
}
