public class Node {
    private char data;
    private Node next;
    private Node prev;

    //constructor
    public Node(char data) { this.data = data; }
    //acessor/mutator
    public void setNext(Node addNext) { this.next = addNext; }
    public void setPrev(Node addPrev) { this.prev = addPrev; }
    public Node getNext() { return this.next; }
    public Node getPrev() { return this.prev; }
    public char getData() { return this.data; }

}
