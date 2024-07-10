package model.board;

import observer.ListenableObject;

import java.util.Arrays;

/**
 * <b>
 * Board is a class that represent the board of the game.
 * </b>
 *
 * <p>
 * A board is a two-dimensional grid in which the players play the game. The board
 * is composed of a specific number of rows and columns. The number of rows and columns
 * is defined by the game.
 * </p>
 *
 * @author <a href="mailto:22013393@unicaen.fr>Manne Emile KITSOUKOU</a>
 * @version 1.0
 */
public class Board extends ListenableObject {

    /**
     * The size of the board.
     */
    private final int size;

    /**
     * The grid of the board.
     */
    private final int[][] grid;

    /**
     * <b>
     * Creates a new board with the given size.
     * </b>
     *
     * @param size the size of the board
     * @throws IllegalArgumentException if the size is less than 3
     */
    public Board(int size) {
        super("updateCell");
        this.size = size;
        this.grid = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = -1;
            }
        }
    }

    /**
     * <b>
     * Creates a new board with the given size and the given grid.
     * </b>
     *
     * @param size the size of the board
     * @param grid the grid of the board
     *             (the grid must be a square matrix)
     */
    public Board(int size, int[][] grid) {
        super("updateCell");
        this.size = size;
        this.grid = new int[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(grid[i], 0, this.grid[i], 0, size);
        }
    }

    /**
     * <b>
     * Returns the size of the board.
     * </b>
     *
     * @return the size of the board
     */
    public int getSize() {
        return this.size;
    }

    /**
     * <b>
     * Returns the cell at the given position.
     * </b>
     *
     * @param i the position of the cell
     * @param j the position of the cell
     * @return the cell at the given position
     */
    public int getCell(int i, int j) {
        return this.grid[i][j];
    }

    /**
     * <b>
     * Returns the cell at the given position.
     * </b>
     */
    public int getCell(Point point) {
        return this.getCell(point.getX(), point.getY());
    }

    /**
     * <b>
     * Sets the cell at the given position.
     * </b>
     *
     * @param i    the position of the cell
     * @param j    the position of the cell
     * @param cell the cell to set
     */
    public void setCell(int i, int j, int cell) {
        this.grid[i][j] = cell;
        super.notifyListeners("updateCell", i, j, cell);
    }

    /**
     * <b>
     * Sets the cell at the given position.
     * </b>
     *
     * @param point the position of the cell
     * @param cell  the cell to set
     */
    public void setCell(Point point, int cell) {
        this.setCell(point.getX(), point.getY(), cell);
    }


    /**
     * <b>
     * Returns the grid of the board.
     * </b>
     *
     * @return the grid of the board
     */
    public int[][] getGrid() {
        return this.grid;
    }


    /**
     * <b>
     *     Checks if a cell is empty.
     * </b>
     *
     * @param i the position of the cell
     * @param j the position of the cell
     * @return true if the cell is empty, false otherwise
     */
    public boolean isEmpty(int i, int j) {
        return this.grid[i][j] == -1;
    }

    /**
     * <b>
     *     Checks if a cell is empty.
     * </b>
     *
     * @param point the position of the cell
     * @return true if the cell is empty, false otherwise
     */
    public boolean isEmpty(Point point) {
        return this.isEmpty(point.getX(), point.getY());
    }

    /**
     * <b>
     *     Check if a cell is in the board.
     * </b>
     *
     * @param i the position of the cell
     * @param j the position of the cell
     * @return true if the cell is in the board, false otherwise
     */
    public boolean isInBoard(int i, int j) {
        return i >= 0 && i < this.size && j >= 0 && j < this.size;
    }

    /**
     * <b>
     *     Check if a cell is in the board.
     * </b>
     *
     * @param point the position of the cell
     * @return true if the cell is in the board, false otherwise
     */
    public boolean isInBoard(Point point) {
        return this.isInBoard(point.getX(), point.getY());
    }


    /**
     * <b>
     * Checks if the board is equal to the given object.
     * </b>
     *
     * <p>
     * The board is equal to the given object if the given object is Board and
     * the size of the board is equal to the size of the given board and the grid
     * </p>
     *
     * @param o the object to compare
     * @return true if the board is equal to the given object, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board board = (Board) o;

        if (size != board.size) return false;
        return Arrays.deepEquals(grid, board.grid);
    }

    /**
     * <b>
     * Returns the hash code of the board.
     * </b>
     *
     * @return the hash code of the board
     */
    @Override
    public int hashCode() {
        int result = size;
        result = 31 * result + Arrays.deepHashCode(grid);
        return result;
    }

    /**
     * <b>
     * Returns the string representation of the board.
     * </b>
     *
     * <p>
     * The string representation of the board is a string that contains the grid of the board.
     * In this string, each cell is represented by a character and the characters are separated
     * by a pipe(|). The representation will help us to identify each with a coordinate.
     * </p>
     *
     * @return the string representation of the board
     */
    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        int format = String.valueOf(size).length();

        // First Line will be the column numbers

        builder.append(String.format("%" + format + "s", " "));

        for (int i = 0; i < size; i++) {

            // We add a letter to the column number
            builder.append(String.format("%" + format + "s", (char) (i + 65))).append(" ");

        }

        // Second Line will be the separator (_ _ _ _ _)

        builder.append("\n ").append(String.format("%" + format + "s", " "));

        for (int i = 0; i < size; i++) {

            builder.append(String.format("%" + format + "s", "_".repeat(format))).append(" ");

        }

        // The rest of the lines will be the row numbers and the cells

        for (int i = 0; i < size; i++) {

            builder.append("\n").append(String.format("%" + format + "s", i)).append("|");

            for (int j = 0; j < size; j++) {

                builder.append(grid[i][j] == -1 ? String.format("%" + format + "s", " ") : String.format("%" + format + "s", grid[i][j])).append("|");

            }

        }
        return builder.toString();

    }


}
