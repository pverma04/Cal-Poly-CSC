import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
//import org.junit.*;

public class PartOneTestCases
{
   public static final double DELTA = 0.00001;

   @Test
   public void testCircleImplSpecifics()
           throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
              "getCenter", "getRadius");

      final List<Class> expectedMethodReturns = Arrays.asList(
              Point.class, double.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
              new Class[0], new Class[0]);

      verifyImplSpecifics(Circle.class, expectedMethodNames,
              expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testRectangleImplSpecifics()
           throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
              "getTopLeft", "getBottomRight");

      final List<Class> expectedMethodReturns = Arrays.asList(
              Point.class, Point.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
              new Class[0], new Class[0]);

      verifyImplSpecifics(Rectangle.class, expectedMethodNames,
              expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testPolygonImplSpecifics()
           throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
              "getPoints");

      final List<Class> expectedMethodReturns = Arrays.asList(
              List.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
              new Class[][] {new Class[0]});

      verifyImplSpecifics(Polygon.class, expectedMethodNames,
              expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testUtilImplSpecifics()
           throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
              "perimeter", "perimeter", "perimeter");

      final List<Class> expectedMethodReturns = Arrays.asList(
              double.class, double.class, double.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
              new Class[] {Circle.class},
              new Class[] {Polygon.class},
              new Class[] {Rectangle.class});

      verifyImplSpecifics(Util.class, expectedMethodNames,
              expectedMethodReturns, expectedMethodParameters);
   }
   @Test
   public void testCirclePerim() {
      Point center = new Point(1, 2);
      Circle testCirc = new Circle(center, 2);
      assertEquals((2*Math.PI*2), Util.perimeter(testCirc), DELTA);
   }
   @Test
   public void testRectPerim() {
      Point tL = new Point(0, 5);
      Point bR = new Point(5, 0);
      Rectangle testRect = new Rectangle(tL, bR);
      assertEquals(20, Util.perimeter(testRect), DELTA);
   }
   @Test
   public void testPolyPerim() {
      List<Point> points = new ArrayList<Point>();
      points.add(new Point(0, 0));
      points.add(new Point(3, 4));
      points.add(new Point(6, 0));
      Polygon testPoly = new Polygon(points);
      assertEquals(16, Util.perimeter(testPoly), DELTA);
   }
   @Test
   public void testWhichIsBigger() {
      //New Circle: Perim = 4*PI = 12.556
      Point center = new Point(1, 2);
      Circle testCirc = new Circle(center, 2);

      //New Rectangle: Perim = 20
      Point tL = new Point(0, 5);
      Point bR = new Point(5, 0);
      Rectangle testRect = new Rectangle(tL, bR);

      //New Polygon: Perim = 16
      List<Point> points = new ArrayList<Point>();
      points.add(new Point(0, 0));
      points.add(new Point(3, 4));
      points.add(new Point(6, 0));
      Polygon testPoly = new Polygon(points);

      assertEquals(20, Bigger.whichIsBigger(testCirc, testRect, testPoly), DELTA);
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
