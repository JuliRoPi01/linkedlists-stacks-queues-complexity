package lab;

public class DoublyLinkedListNoTail <T>{
    private Node head = null;
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
        if(!isEmpty()){
            head.prev = node;
            node.next = head;
        }
        head = node;
        size++;
    }

    //O(n)
    public void pushBack(T data){
        Node node = new Node(data);
        if(isEmpty()) head = node;
        else {
            Node p = head;
            while(p.next != null){
                p = p.next;
            }
            p.next = node;
            node.prev = p;
        }
        size ++;
    }

    //O(1)
    public T popFront(){
        if(isEmpty()) throw new Error("Lista esta vacia");
        Node temp = head;
        if(head.next != null){
            head.next.prev = null;
        }
        head = head.next;
        temp.next = null;
        size--;
        return temp.data;
    }

    //O(n)
    public T popBack(){
        if(isEmpty()) throw new Error("Lista esta vacia");
        Node temp = head;
        if(head.next == null){
            head = null;
        } else {
            Node p = head;
            while(p.next.next != null){
                p = p.next;
            }
            temp = p.next;
            temp.prev = null;
            p.next = null;
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
            head = head.next;
            if(head != null) head.prev = null;
            ref.next = null;
            size--;
            return;
        }

        ref.prev.next = ref.next;
        if(ref.next != null) ref.next.prev = ref.prev;
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

    //O(1)
    public void addAfter(Node ref, T data){
        Node nodo = new Node(data);
        nodo.prev = ref;
        nodo.next = ref.next;
        if(nodo.next != null) nodo.next.prev = nodo;
        ref.next = nodo;
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
