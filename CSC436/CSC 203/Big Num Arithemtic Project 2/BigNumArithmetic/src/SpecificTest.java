import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SpecificTest {
    //LinkedList class
    @Test
    public void testSize() {
        LinkedList l = new LinkedList();
        l.addToEnd(new Node(0));
        l.addToEnd(new Node(1));
        l.addToEnd(new Node(2));
        l.addToEnd(new Node(3));
        l.addToEnd(new Node(4));
        l.addToEnd(new Node(5));
        l.addToEnd(new Node(6));
        int answer = 7;
        assertEquals(answer, l.size());
    }
    @Test
    public void testGetNodeAt() {
        LinkedList l = new LinkedList();
        l.addToEnd(new Node(0));
        l.addToEnd(new Node(1));
        l.addToEnd(new Node(2));
        l.addToEnd(new Node(3));
        l.addToEnd(new Node(4));
        l.addToEnd(new Node(5));
        l.addToEnd(new Node(6));
        Node answer = new Node(5);
        assertEquals(answer.getData(), l.getNodeAt(5).getData());
    }
    @Test
    public void testClear() {
        LinkedList l = new LinkedList();
        l.addToEnd(new Node(0));
        l.addToEnd(new Node(1));
        l.addToEnd(new Node(2));
        l.addToEnd(new Node(3));
        l.addToEnd(new Node(4));
        l.addToEnd(new Node(5));
        l.addToEnd(new Node(6));
        assertEquals(7, l.size());
        l.clear();
        assertEquals(0, l.size());
    }
    @Test
    public void testAddToEnd() {
        LinkedList l = new LinkedList();
        l.addToEnd(new Node(0));
        l.addToEnd(new Node(1));
        l.addToEnd(new Node(2));
        l.addToEnd(new Node(3));
        l.addToEnd(new Node(4));
        l.addToEnd(new Node(5));
        l.addToEnd(new Node(6));
        Node answer = l.getTail();
        assertEquals('6', answer.getData());
        l.addToEnd(new Node(7));
        answer = l.getTail();
        assertEquals('7', answer.getData());
    }
    @Test
    public void testAddToFront() {
        LinkedList l = new LinkedList();
        l.addToEnd(new Node(0));
        l.addToEnd(new Node(1));
        l.addToEnd(new Node(2));
        l.addToEnd(new Node(3));
        l.addToEnd(new Node(4));
        l.addToEnd(new Node(5));
        l.addToEnd(new Node(6));
        Node answer = l.getHead();
        assertEquals('0', answer.getData());
        l.addToFront(new Node(9));
        answer = l.getHead();
        assertEquals('9', answer.getData());
    }
    @Test
    public void testToString() {
        LinkedList l = new LinkedList();
        l.addToEnd(new Node(0));
        l.addToEnd(new Node(1));
        l.addToEnd(new Node(2));
        l.addToEnd(new Node(3));
        l.addToEnd(new Node(4));
        l.addToEnd(new Node(5));
        l.addToEnd(new Node(6));
        String answer = "0123456";
        assertEquals(answer, l.toString());

    }


    //Number class
    @Test
    public void testAdd1() throws NumberException {
        Number testNum1 = new Number("839183");
        Number testNum2 = new Number("8273940");
        Number answer = testNum1.add(testNum2);
        assertEquals("9113123", answer.toString());
    }
    @Test
    public void testAdd2() throws NumberException {
        Number testNum1 = new Number("12342239713");
        Number testNum2 = new Number("827239741233940");
        Number answer = testNum1.add(testNum2);
        assertEquals("827252083473653", answer.toString());
    }
    @Test
    public void testMultiply1() throws NumberException {
        Number testNum1 = new Number("12342239713");
        Number testNum2 = new Number("827239741233940");
        Number answer = testNum1.multiply(testNum2);
        assertEquals("10209991186429377891459220", answer.toString());
    }
    @Test
    public void testMultiply2() throws NumberException {
        Number testNum1 = new Number("9182949202");
        Number testNum2 = new Number("3284478392");
        Number answer = testNum1.multiply(testNum2);
        assertEquals("30161198228802643184", answer.toString());
    }

    @Test
    public void testExponent1() throws NumberException {
         Number testNum1 = new Number("15");
         Number testNum2 = new Number("12");
         Number answer = testNum1.exponent(testNum2);
         assertEquals("129746337890625", answer.toString());
    }
    @Test
    public void testExponent2() throws NumberException {
        Number testNum1 = new Number("124");
        Number testNum2 = new Number("4");
        Number answer = testNum1.exponent(testNum2);
        assertEquals("236421376", answer.toString());
    }
    @Test
    public void testToString1() throws NumberException {
        Number testNum1 = new Number("12419398429842");
        assertEquals("12419398429842", testNum1.toString());
    }
    @Test
    public void testToString2() throws NumberException {
        Number testNum1 = new Number("201384209431");
        assertEquals("201384209431", testNum1.toString());
    }
    @Test
    public void testSetValueChar1() throws NumberException {
        Number testNum1 = new Number("201384209431");
        testNum1.setValue('1');
        assertEquals("1", testNum1.toString());
    }
    @Test
    public void testSetValueChar2() throws NumberException {
        Number testNum1 = new Number("2130141");
        testNum1.setValue('3');
        assertEquals("3", testNum1.toString());
    }
    @Test
    public void testSetValueStr() throws NumberException {
        Number testNum1 = new Number("2130141");
        testNum1.setValue("02974017240912");
        assertEquals("02974017240912", testNum1.toString());
    }
}
