package lab;

public class SinglyLinkedListWithTail <T>{

    private Node head = null;
    private Node tail = null;
    private int size;

    private class Node {
        public T data;
        public Node next = null;
        
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
            node.next = head;
        }
        head = node;
        size++;
    }

    //O(1)
    public void pushBack(T data){
        Node node = new Node(data);
        if(isEmpty()) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
        size ++;
    }

    //O(1)
    public T popFront(){
        if(isEmpty()) throw new Error("Lista esta vacia");
        Node temp = head;
        if(head == tail) tail = null;
        head = head.next;
        temp.next = null;
        size--;
        return temp.data;
    }
    
    //O(n)
    public T popBack(){
        if(isEmpty()) throw new Error("Lista esta vacia");
        Node temp = head;
        if(head == tail){
            head = null;
            tail = null;
        } else {
            Node p = head;
            while(p.next.next != null){
                p = p.next;
            }
            temp = p.next;
            p.next = null;
            tail = p;
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

    //O(n)
    public void erase(Node ref){
        if(isEmpty()) throw new Error("Lista esta vacia");
        Node p = head;
        if(head == ref){
            if(head == tail) tail = null;
            head = head.next;
            p.next = null;
            size--;
            return;
        }

        while(p.next != null && p.next != ref){
            p = p.next;
        }
        if(tail == ref) tail = p;
        p.next = ref.next;
        ref.next = null;
        size--;
    }
     
    //O(n)
    public void addBefore(Node ref, T data){
        if(ref == head) {
            pushFront(data);
            return;
        }
        Node nodo = new Node(data);
        Node p = head;
        while(p.next != null && p.next != ref){
            p = p.next;
        }
        if(p.next == null) throw new Error("Referencia no encontrada");
        nodo.next = ref;
        p.next = nodo;
        size++;
    }

    //O(1)
    public void addAfter(Node ref, T data){
        Node nodo = new Node(data);
        nodo.next = ref.next;
        ref.next = nodo;
        if(ref == tail) tail = ref.next;
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
            System.out.print(p.data + " -> ");
            p = p.next;
        }
        System.out.println(p.data);    
    }
}
