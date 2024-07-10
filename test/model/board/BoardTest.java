package model.board;

import junit.framework.TestCase;

import model.board.Board;

import model.board.Point;

import java.util.Arrays;

import java.util.List;

public class BoardTest extends TestCase {

    // Test de la méthode getSize()

    public void testGetSize() {

        Board board = new Board(3);

        assertEquals(3, board.getSize());

    }

    // Test de la méthode getCell()

    public void testGetCell() {

        Board board = new Board(3);

        assertEquals(-1, board.getCell(new Point(1, 1)));

    }

    // Test de la méthode setCell()

    public void testSetCell() {

        Board board = new Board(3);

        board.setCell(new Point(1, 1), -1);

        assertEquals(-1, board.getCell(new Point(1, 1)));

    }

    // Test de la méthode equals()

    public void testEquals() {

        Board board1 = new Board(3);

        Board board2 = new Board(3);

        assertEquals(board1, board2);

    }

    // // Test de la méthode toString()

    // public void testToString() {

    //     Board board = new Board(3);

    //     assertEquals("   0 1 2 \r 0  | | \r 1  | | \r 2  | | \r", board.toString());

    // }

    // Test de la méthode hashCode()

    public void testHashCode() {

        Board board = new Board(3);

        assertEquals(31 * board.getSize() + Arrays.deepHashCode(board.getGrid()), board.hashCode());

    }

    public void testHashCode2() {

        Board board = new Board(3);

        Board board2 = new Board(3);

        assertEquals(board.hashCode(), board2.hashCode());

        board2.setCell(new Point(1, 1), 0);

    }

    // Test de la méthode getGrid()

    public void testGetGrid() {

        Board board = new Board(3);

        assertEquals(3, board.getGrid().length);

    }

}
