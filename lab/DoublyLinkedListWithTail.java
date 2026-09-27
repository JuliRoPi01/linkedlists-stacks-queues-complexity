package lab;

public class DoublyLinkedListWithTail <T>{
    private Node head = null;
    private Node tail = null;
    private int size;

    private class Node {
        public T data;
        public Node next = null;
        public Node prev = null;
        
        public Node(T data){
            this.data = data;
        }
    }
    //O(1)
    public void pushFront(T data){
        Node node = new Node(data);
        if(isEmpty()){
            tail = node;
        } else {
            head.prev = node;
            node.next = head;
        }
        head = node;
        size++;
    }

    //O(1)
    public void pushBack(T data){
        Node node = new Node(data);
        if(isEmpty()){
            head = node;
        } else {
            node.prev = tail;
            tail.next = node;
        }
        tail = node;
        size ++;
    }

    //O(1)
    public T popFront(){
        if(isEmpty()) throw new Error("Lista esta vacia");
        Node temp = head;
        if(head == tail){
            tail = null;
        } else {
            head.next.prev = null;
        }
        head = head.next;
        temp.next = null;
        size--;
        return temp.data;
    }

    //O(1)
    public T popBack(){
        if(isEmpty()) throw new Error("Lista esta vacia");
        Node temp = tail;
        if(head == tail){
            head = null;
            tail = null;
        } else {
            tail.prev.next = null;
            tail = tail.prev;
            temp.prev = null;
        }
        size--;
        return temp.data;
    }
    
    //O(n)
    public Node find(T data){
        if(isEmpty()) throw new Error("Lista esta vacia");
        Node p = head;
        while(p != null && !p.data.equals(data)){
            p = p.next;
        }
        if(p == null) return null;
        return p;
    }

    //O(1)
    public void erase(Node ref){
        if(isEmpty()) throw new Error("Lista esta vacia");
        if(head == ref){
            popFront();
            return;
        } else if (tail == ref){
            popBack();
            return;
        }

        ref.prev.next = ref.next;
        ref.next.prev = ref.prev;
        ref.next = null;
        ref.prev = null;
        size--;
    }
 
    //O(1)
    public void addBefore(Node ref, T data){
        if(ref == head) {
            pushFront(data);
            return;
        }
        Node nodo = new Node(data);
        nodo.next = ref;
        nodo.prev = ref.prev;
        nodo.prev.next = nodo;
        ref.prev = nodo;
        size++;
    }

    public void addAfter(Node ref, T data){
        if(ref == tail){
            pushBack(data);
            return;
        }
        Node nodo = new Node(data);
        nodo.prev = ref;
        nodo.next = ref.next;
        ref.next = nodo;
        nodo.next.prev = nodo;
        size++;
    }

    public boolean isEmpty(){
        return head == null;
    }

    public void printList(){
        if(isEmpty()) throw new Error("Lista vacia");
        Node p = head;
        System.out.println("Tamaño: " + size);
        while(p.next != null){
            System.out.print(p.data + " <-> ");
            p = p.next;
        }
        System.out.println(p.data);    
    }
}
