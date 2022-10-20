import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
//
//    @Test
//    public void testAddZeros() throws NumberException {
//        Number testNum1 = new Number("638");
//        Number answer = testNum1.addZeros(testNum1, 5, true);
//        assertEquals("00000638", answer.toString());
//    }
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
