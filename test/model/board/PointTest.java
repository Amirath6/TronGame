package model.board;

import junit.framework.TestCase;
import model.board.Point;

import java.util.HashSet;
import java.util.Set;

public class PointTest extends TestCase {

    public void testConstructor() {
        Point point = new Point(1, 2);
        assertEquals(1, point.getX());
        assertEquals(2, point.getY());
    }

    public void testGetDirections() {
        Set<Point> expected = new HashSet<>(Set.of(
                new Point(0, 1),
                new Point(1, 0),
                new Point(0, -1),
                new Point(-1, 0)
        ));
        assertEquals(expected, Point.getDirections());
    }

    public void testEquals() {
        Point point1 = new Point(1, 2);
        Point point2 = new Point(1, 2);
        assertEquals(point1, point2);
    }

    public void testNotEquals() {
        Point point1 = new Point(1, 2);
        Point point2 = new Point(2,2);
        Point point3 = new Point(1, 3);
        assertNotSame(point1, point2);
        assertNotSame(point1, point3);
    }

    public void testHashCode() {
        Point p1 = new Point(1, 2);
        Point p2 = new Point(1, 2);
        Point p3 = new Point(2, 2);
        Point p4 = new Point(1, 3);

        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotSame(p1.hashCode(), p3.hashCode());
        assertNotSame(p1.hashCode(), p4.hashCode());

    }

    public void testToString() {
        Point point = new Point(1, 2);
        assertEquals("(1, 2)", point.toString());
    }

}
