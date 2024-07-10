package model;

import java.util.HashSet;
import java.util.Set;

import model.board.Point;

/**
 * <b>
 *     Move is a class that represents a move.
 * </b>
 * 
 * <p>
 *    A move is a move that is made by a player. It is composed of a head, a direction and a length.
 * </p>
 * 
 * @author <a href="mailto:22013393@unicaen.fr">Manne Emile KITSOUKOU</a>
 * @version 1.0
 */
public class Move {

    /**
     * The head of the move.
     */
    private final Point head;

    /**
     * The direction of the move.
     */
    private final Point direction;

    /**
     * The length of the move.
     */
    private int length;

    /**
     * Builds a new move with the given head, direction and length.
     */
    public Move(Point head, Point direction, int length) {
        this.head = head;
        this.direction = direction;
        this.length = length;
    }

    /**
     * Builds a new move with the given head and direction.
     */
    public Move(Point head, Point direction) {
        this(head, direction, 1);
    }

    /**
     * Builds a new move with the given head.
     */
    public Move(Point head) {
        this(head, new Point(0, 0));
    }

    /**
     * Builds a new move with the given move.
     */
    public Move(Move move) {
        this(move.getHead(), move.getDirection(), move.getLength());
    }

    /**
     * Increments the length of the move.
     */
    public void incrementLength() {
        this.length++;
    }

    /**
     * Returns the head of the move.
     */
    public Point getHead() {
        return this.head;
    }

    /**
     * Returns the direction of the move.
     */
    public Point getDirection() {
        return this.direction;
    }

    /**
     * Returns the length of the move.
     */
    public int getLength() {
        return this.length;
    }


    @Override
    public String toString() {
        return "Move{" + "head=" + head + ", direction=" + direction + ", length=" + length + '}';
    }

    /**
     * Returns possible moves from the head of the move.
     *
     * @return possible moves from the head of the move
     */
    public Set<Move> getPossibleMoves() {

        // Set of all possible movements
        Set<Move> possibleMoves = new HashSet<>();

        // Set of all possible directions
        Set<Point> directions = Point.getDirections();

        // Remove the opposite direction
        directions.remove(new Point(-this.direction.getX(), -this.direction.getY()));

        // For each possible direction 
        for (Point possibleDirection : directions) {

            // Create a new move with the head of the move and the possible direction and add it to the set of possible moves 
            Move move = new Move(new Point(this.head.getX() + possibleDirection.getX(), this.head.getY() + possibleDirection.getY()), possibleDirection);

            // Add the move to the set of possible moves
            possibleMoves.add(move);

            // If the direction of the move is the same as the direction of the move, increment the length of the move
            if (move.getDirection().equals(this.direction)) {
                move.incrementLength();
            }
        }
        return possibleMoves;
    }

    /**
     * <b>
     *     Returns an arbitrary move in the same direction as the move.
     * </b>
     *
     * @return an arbitrary move in the same direction as the move
     */
    public Move getArbitraryMove() {

        // Return a new move with the head of the move and the direction of the move
        return new Move(new Point(this.head.getX() + this.direction.getX(), this.head.getY() + this.direction.getY()), this.direction, this.length+1);
    }


}
