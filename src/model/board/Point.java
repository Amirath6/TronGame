package model.board;

import java.util.HashSet;
import java.util.Set;

/**
 * <b>
 * Point is a class that represent the position of a specific element
 * </b>
 *
 * <p>
 * A point is an euclidean point in a two-dimensional space. It is defined
 * by two coordinates: the x-coordinate and the y-coordinate. The x-coordinate
 * is the abscissa and the y-coordinate is the ordinate.
 * </p>
 *
 * <p>
 * A point help us to locate a specific element in a two-dimensional space(like a grid for example)
 * </p>
 *
 * @author <a href="mailto:22013393@unicaen.fr">Manne Emile KITSOUKOU</a>
 * @version 1.0
 */
public class Point {

    /**
     * The x-coordinate of the point.
     */
    private final int x;

    /**
     * The y-coordinate of the point.
     */
    private final int y;

    /**
     * <b>
     * Creates a new point with the given coordinates.
     * </b>
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Set<Point> getDirections() {
        return new HashSet<>(Set.of(
                new Point(0, 1),
                new Point(1, 0),
                new Point(0, -1),
                new Point(-1, 0)
        ));
    }

    /**
     * <b>
     * Returns the x-coordinate of the point.
     * </b>
     *
     * @return the x-coordinate of the point
     */
    public int getX() {
        return x;
    }

    /**
     * <b>
     * Returns the y-coordinate of the point.
     * </b>
     *
     * @return the y-coordinate of the point
     */
    public int getY() {
        return y;
    }

    /**
     * <b>
     * Returns the string representation of the point.
     * </b>
     *
     * @return the string representation of the point
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * <b>
     * Returns true if the given object is equal to this point.
     * </b>
     *
     * <p>
     * Two points are equal if they have the same coordinates.
     * </p>
     *
     * @param o the object to be compared with this point
     * @return true if the given object is equal to this point
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (x != point.x) return false;
        return y == point.y;
    }

    /**
     * <b>
     * Returns the hash code of the point.
     * </b>
     *
     * @return the hash code of the point
     */
    @Override
    public int hashCode() {
        int result = 7;
        result = 31 * result + x;
        result = 31 * result + y;
        return result;
    }

}
