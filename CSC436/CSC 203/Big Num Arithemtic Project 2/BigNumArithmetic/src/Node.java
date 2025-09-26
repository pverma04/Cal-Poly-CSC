public class Node {
    private char data;
    private Node next;
    private Node prev;
    private final int RADIX = 10;

    //constructor
    public Node(char data) { this.data = data; }
    public Node(int data) { this.data = Character.forDigit(data, this.RADIX); }
    //acessor/mutator
    public void setNext(Node addNext) { this.next = addNext; }
    public void setPrev(Node addPrev) { this.prev = addPrev; }
    public Node getNext() { return this.next; }
    public Node getPrev() { return this.prev; }
    public char getData() { return this.data; }

}
