import java.util.List;

public class LinkedList {
    private Node head;
    private Node tail;

    public LinkedList() {
        this.head = null;
        this.tail = null;
    }
    public Node getHead() { return this.head; }
    public Node getTail() { return this.tail; }
    public void addToEnd(Node add) {
        if(this.head == null) {
            this.head = add;
            this.head = add;
            this.head.setPrev(null);
            this.tail = add;
            this.tail.setNext(null);
        }
        else{
            this.tail.setNext(add);
            add.setPrev(this.tail);
            add.setNext(null);
            this.tail = add;
        }
    }

    public void printList(){
        Node p = this.head; //to traverse
        if(p != null){
            while (p != null) { //traverse all the way to the end
                System.out.println("" + p.getData());
                p = p.getNext();
            }
        }
        else{
            System.out.println("Empty List");
        }
    }

    public int size() {
        int count = 0;
        Node p = this.head;
        while (p != null) {
            count++;
            p = p.getNext();
        }
        return count;
    }
    public Node getNodeAt(int index) {
        Node p = this.head;

        for(int i = 0; i < index; i++) {
            if(p.getNext() != null) {
               p = p.getNext();
            }
        }
        return p;
    }
    public void clear() {
        this.head = null;
    }
}
