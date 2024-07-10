package model.board;

import junit.framework.TestCase;
import org.junit.Before;

import java.util.Arrays;

public class BoardTest2 extends TestCase{

    /**
     * The size of the board
     */
    private static final int SIZE = 3;

    /**
     * The initial cell value
     */
    private static final int INITIAL_CELL_VALUE = -1;

    /**
     * The new cell value
     */
    private static final int NEW_CELL_VALUE = 1;

    public void testGetSize() {
        Board board = new Board(SIZE);
        assertEquals(SIZE, board.getSize());
    }

    public void testGetCell() {
        Board board = new Board(SIZE);
        assertEquals(INITIAL_CELL_VALUE, board.getCell(1, 1));
    }

    public void testSetCell() {
        Board board = new Board(SIZE);
        board.setCell(1, 1, NEW_CELL_VALUE);
        assertEquals(NEW_CELL_VALUE, board.getCell(1, 1));
    }

    public void testGetGrid() {
        Board board = new Board(SIZE);
        int[][] grid = board.getGrid();
        assertEquals(SIZE, grid.length);
        for (int i = 0; i < SIZE; i++) {
            assertEquals(SIZE, grid[i].length);
            for (int j = 0; j < SIZE; j++) {
                assertEquals(INITIAL_CELL_VALUE, grid[i][j]);
            }
        }
    }

    public void testEquals() {
        Board board1 = new Board(SIZE);
        Board board2 = new Board(SIZE);
        assertEquals(board1, board2);

        board1.setCell(1, 1, NEW_CELL_VALUE);
        assertNotSame(board1, board2);
    }

    public void testHashCode() {
        Board board = new Board(SIZE);
        assertEquals(31 * board.getSize() + Arrays.deepHashCode(board.getGrid()), board.hashCode());
    }
}
