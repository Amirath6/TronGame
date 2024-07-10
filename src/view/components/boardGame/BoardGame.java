package view.components.boardGame;

import model.board.Board;
import model.board.Point;
import observer.Listenable;
import observer.Listener;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;

/**
 * BoardGame is the class that represents the board of the game.
 * It is responsible for the display of the board.
 *
 * @author <a href="mailto:sdoumbouya633@gmail.com"> Sekou DOUMBOUYA </a>
 * @version 1.0
 */

public class BoardGame extends JPanel implements Listener {


    /**
     * The board of the game.
     */

    private  Board board;

    /**
     * Builds a new board component with the given board.
     *
     * @param board the board of the game
     */

    public BoardGame(Board board) {
        this.board = board;
        this.board.addListener(this);
        build();

    }

    @Override

    public void notify(Listenable listenable, String s, Object... objects) {

        if (s.equals("updateCell")) {

            int x = (int) objects[0];

            int y = (int) objects[1];

            int player = (int) objects[2];

            CellComponent cell = this.getCellComponent(x, y);

            cell.setPlayer(player);

            cell.repaint();

        }

    }

    public void build() {

        // Fix the layout of the board

        this.setLayout(new GridLayout(board.getSize() + 1 , board.getSize() + 1));

        this.setBorder(BorderFactory.createCompoundBorder(
                
                    BorderFactory.createCompoundBorder(
                            BorderFactory.createEmptyBorder(5, 5, 5, 5),

                            BorderFactory.createLoweredBevelBorder()
        
                    ),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
    
            ));

        
        // Label the columns of the board with letters
        this.add(new JLabel(" "));

        for (int i = 0; i < board.getSize(); i++) {

            JLabel label = new JLabel(" " + (char) ('A' + i) + " ");

            label.setHorizontalAlignment(SwingConstants.CENTER);

            label.setVerticalAlignment(SwingConstants.CENTER);

            this.add(label);

        }

        // Create the cells of the board and label the rows of the board with numbers


        for (int i = 0; i < board.getSize(); i++) {

            JLabel label = new JLabel(" " + (i + 1) + " ");

            label.setHorizontalAlignment(SwingConstants.CENTER);

            label.setVerticalAlignment(SwingConstants.CENTER);

            this.add(label);

            for (int j = 0; j < board.getSize(); j++) {

                CellComponent cell = new CellComponent(board.getCell(i, j));

                this.add(cell);

            }

        }

        

    }

    public CellComponent getCellComponent(int i, int j) {

        // Note: the first component of the board is the label of the columns and the first row is the label of the rows

        return (CellComponent) this.getComponent((i + 1) * (board.getSize() + 1) + j + 1);
    }

    public CellComponent getCellComponent(Point point) {

        return getCellComponent(point.getX(), point.getY());

    }

    public Board getBoard() {

        return board;

    }


    /**
     * Set the board of the game.
     * @param board the board of the game
     */
    public void setBoard(Board board) {
        this.board = board;
    }


}       
