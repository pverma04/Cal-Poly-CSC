import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

import java.awt.Color;
import java.awt.Point;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

public class TestCases
{
   public static final double DELTA = 0.00001;

   /* some sample tests but you must write more! see lab write up */

   @Test
   public void testCircleGetArea()
   {
      Circle c = new Circle(5.678, new Point(2, 3), Color.BLACK);

      assertEquals(101.2839543, c.getArea(), DELTA);
   }

   @Test
   public void testCircleGetPerimeter()
   {
      Circle c = new Circle(5.678, new Point(2, 3), Color.BLACK);

      assertEquals(35.6759261, c.getPerimeter(), DELTA);
   }
   @Test
   public void testCircleEquals()
   {
      Circle c = new Circle(5.678, new Point(2, 3), Color.BLACK);
      Circle a = new Circle(5.678, new Point(2, 3), Color.BLACK);

      assertEquals("true", String.valueOf(c.equals(a)));
   }

   @Test
   public void testRectangleArea()
   {
      Rectangle r = new Rectangle(10, 5, new Point(0, 0), Color.BLACK);

      assertEquals(50, r.getArea(), DELTA);
   }

   @Test
   public void testRectangleGetPerimeter()
   {
      Rectangle r = new Rectangle(10, 5, new Point(0, 0), Color.BLACK);

      assertEquals(30, r.getPerimeter(), DELTA);
   }
   @Test
   public void testRectangleEquals()
   {
      Rectangle r = new Rectangle(10, 5, new Point(0, 0), Color.BLACK);
      Rectangle a = new Rectangle(10, 5, new Point(0, 0), Color.BLACK);

      assertEquals("true", String.valueOf(r.equals(a)));
   }

   @Test
   public void testTriangleArea()
   {
      Triangle t = new Triangle(new Point(0, 0), new Point(10, 0), new Point(5, 5), Color.BLACK);

      assertEquals(25, t.getArea(), DELTA);
   }

   @Test
   public void testTriangleGetPerimeter()
   {
      Triangle t = new Triangle(new Point(0, 0), new Point(10, 0), new Point(5, 5), Color.BLACK);

      assertEquals(24.14213562373095, t.getPerimeter(), DELTA);
   }
   @Test
   public void testTriangleEquals()
   {
      Triangle t = new Triangle(new Point(0, 0), new Point(10, 0), new Point(5, 5), Color.BLACK);
      Triangle a = new Triangle(new Point(0, 0), new Point(10, 0), new Point(5, 5), Color.BLACK);

      assertEquals("true", String.valueOf(t.equals(a)));
   }


   @Test
   public void testWorkSpaceAreaOfAllShapes()
   {
      WorkSpace ws = new WorkSpace();

      ws.add(new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK));
      ws.add(new Circle(5.678, new Point(2, 3), Color.BLACK));
      ws.add(new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0), 
                 Color.BLACK));

      assertEquals(114.2906063, ws.getAreaOfAllShapes(), DELTA);
   }
   @Test
   public void testWorkSpacePerimeterOfAllShapes()
   {
      WorkSpace ws = new WorkSpace();

      ws.add(new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK));
      ws.add(new Circle(5.678, new Point(2, 3), Color.BLACK));
      ws.add(new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0),
              Color.BLACK));

      assertEquals(61.09516775478293, ws.getPerimeterOfAllShapes(), DELTA);
   }

   @Test
   public void testWorkSpaceGetCircles()
   {
      WorkSpace ws = new WorkSpace();
      List<Circle> expected = new LinkedList<>();

      // Have to make sure the same objects go into the WorkSpace as
      // into the expected List since we haven't overriden equals in Circle.
      Circle c1 = new Circle(5.678, new Point(2, 3), Color.BLACK);
      Circle c2 = new Circle(1.11, new Point(-5, -3), Color.RED);

      ws.add(new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK));
      ws.add(c1);
      ws.add(new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0),
                 Color.BLACK));
      ws.add(c2);

      expected.add(c1);
      expected.add(c2);

      // Doesn't matter if the "type" of lists are different (e.g Linked vs
      // Array).  List equals only looks at the objects in the List.
      assertEquals(expected, ws.getCircles());
   }
   @Test
   public void testWorkSpaceGetRectangles()
   {
      WorkSpace ws = new WorkSpace();
      List<Rectangle> expected = new LinkedList<>();

      // Have to make sure the same objects go into the WorkSpace as
      // into the expected List since we haven't overriden equals in Circle.
      Rectangle r1 = new Rectangle(5.678, 5, new Point(1, 10), Color.BLACK);
      Rectangle r2 = new Rectangle(1.11, 6, new Point(2, 20), Color.RED);

      ws.add(new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK));
      ws.add(r1);
      ws.add(new Circle(5.678, new Point(2, 3), Color.BLACK));
      ws.add(new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0),
              Color.BLACK));
      ws.add(r2);

      expected.add(new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK));
      expected.add(r1);
      expected.add(r2);


      // Doesn't matter if the "type" of lists are different (e.g Linked vs
      // Array).  List equals only looks at the objects in the List.
      assertEquals(expected, ws.getRectangles());
   }
   @Test
   public void testWorkSpaceGetTriangles()
   {
      WorkSpace ws = new WorkSpace();
      List<Triangle> expected = new LinkedList<>();

      // Have to make sure the same objects go into the WorkSpace as
      // into the expected List since we haven't overriden equals in Circle.
      Triangle t1 = new Triangle(new Point(1,0), new Point(3,-4), new Point(4, 0), Color.BLACK);
      Triangle t2 = new Triangle(new Point(10,15), new Point(13,-14), new Point(14, 10), Color.BLACK);

      ws.add(new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK));
      ws.add(t1);
      ws.add(new Circle(5.678, new Point(2, 3), Color.BLACK));
      ws.add(new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0),
              Color.BLACK));
      ws.add(t2);

      expected.add(t1);
      expected.add(new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0), Color.BLACK));
      expected.add(t2);


      // Doesn't matter if the "type" of lists are different (e.g Linked vs
      // Array).  List equals only looks at the objects in the List.
      assertEquals(expected, ws.getTriangles());
   }
   public void testWorkSpaceGetByColor()
   {
      WorkSpace ws = new WorkSpace();
      List<Shape> expected = new LinkedList<>();

      Triangle t1 = new Triangle(new Point(1,0), new Point(3,-4), new Point(4, 0), Color.RED);
      Triangle t2 = new Triangle(new Point(10,15), new Point(13,-14), new Point(14, 10), Color.RED);

      ws.add(new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK));
      ws.add(t1);
      ws.add(new Circle(5.678, new Point(2, 3), Color.RED));
      ws.add(new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0),
              Color.BLACK));
      ws.add(t2);

      expected.add(t1);
      expected.add(new Circle(5.678, new Point(2, 3), Color.RED));
      expected.add(t2);



      assertEquals(expected, ws.getShapesByColor(Color.RED));
   }

   /* HINT - comment out implementation tests for the classes that you have not 
    * yet implemented */
   @Test
   public void testCircleImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getColor", "setColor", "getArea", "getPerimeter", "translate",
         "getRadius", "setRadius", "getCenter", "equals");

      final List<Class> expectedMethodReturns = Arrays.asList(
         Color.class, void.class, double.class, double.class, void.class,
         double.class, void.class, Point.class, boolean.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {Point.class},
         new Class[0], new Class[] {double.class}, new Class[0], new Class[] {Object.class});

      verifyImplSpecifics(Circle.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testRectangleImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getColor", "setColor", "getArea", "getPerimeter", "translate",
         "getWidth", "setWidth", "getHeight", "setHeight", "getTopLeft", "equals");

      final List<Class> expectedMethodReturns = Arrays.asList(
         Color.class, void.class, double.class, double.class, void.class,
         double.class, void.class, double.class, void.class, Point.class, boolean.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {Point.class},
         new Class[0], new Class[] {double.class}, new Class[0], new Class[] {double.class}, 
         new Class[0], new Class[] {Object.class});

      verifyImplSpecifics(Rectangle.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testTriangleImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getColor", "setColor", "getArea", "getPerimeter", "translate",
         "getVertexA", "getVertexB", "getVertexC", "equals");

      final List<Class> expectedMethodReturns = Arrays.asList(
         Color.class, void.class, double.class, double.class, void.class,
         Point.class, Point.class, Point.class, boolean.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {Point.class},
         new Class[0], new Class[0], new Class[0], new Class[] {Object.class});

      verifyImplSpecifics(Triangle.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   private static void verifyImplSpecifics(
      final Class<?> clazz,
      final List<String> expectedMethodNames,
      final List<Class> expectedMethodReturns,
      final List<Class[]> expectedMethodParameters)
      throws NoSuchMethodException
   {
      assertEquals("Unexpected number of public fields",
         0, clazz.getFields().length);

      final List<Method> publicMethods = Arrays.stream(
         clazz.getDeclaredMethods())
            .filter(m -> Modifier.isPublic(m.getModifiers()))
            .collect(Collectors.toList());

      assertEquals("Unexpected number of public methods",
         expectedMethodNames.size(), publicMethods.size());

      assertTrue("Invalid test configuration",
         expectedMethodNames.size() == expectedMethodReturns.size());
      assertTrue("Invalid test configuration",
         expectedMethodNames.size() == expectedMethodParameters.size());

      for (int i = 0; i < expectedMethodNames.size(); i++)
      {
         Method method = clazz.getDeclaredMethod(expectedMethodNames.get(i),
            expectedMethodParameters.get(i));
         assertEquals(expectedMethodReturns.get(i), method.getReturnType());
      }
   }
}
