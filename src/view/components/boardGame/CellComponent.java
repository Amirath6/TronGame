package view.components.boardGame;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * CellComponent is the class that represents a cell of the board.
 * <p>
 * It is responsible for the display of the cell.
 *
 * @author <a href="mailto:sdoumbouya633@gmail.com"> Sekou DOUMBOUYA </a>
 * @version 1.0
 */

public class CellComponent extends JPanel {

    /**
     * The random number generator.
     */

    private static final double startValue = new Random().nextDouble();
    /**
     * The player of the cell.
     */

    private int player;
    /**
     * A boolean that indicates if the cell is currently selected.
     */

    private boolean selected;

    /**
     * Builds a new cell component with the given player.
     *
     * @param player the player of the cell
     */

    public CellComponent(int player) {

        this.player = player;

        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));


        this.selected = false;

    }

    /**
     * Builds a new cell component with no player.
     */

    public CellComponent() {

        this(-1);

    }

    public static Color getColor(int player) {

        double phi = (1 + Math.sqrt(5)) / 2;

        double h = startValue;

        h += phi * player;

        h %= 1;

        return Color.getHSBColor((float) h, 0.8f, 0.8f);

    }

    /**
     * Returns the player of the cell.
     *
     * @return the player of the cell
     */

    public int getPlayer() {

        return this.player;

    }

    /**
     * Sets the player of the cell.
     *
     * @param player the player of the cell
     */

    public void setPlayer(int player) {

        this.player = player;

    }

    /**
     * Returns true if the cell is currently selected.
     *
     * @return true if the cell is currently selected
     */

    public boolean isSelected() {

        return this.selected;

    }

    /**
     * Sets the selected state of the cell.
     *
     * @param selected the selected state of the cell
     */

    public void setSelected(boolean selected) {

        this.selected = selected;

    }

    /**
     * Draw a circle in the cell.
     *
     * @param g the graphics
     */

    public void drawCircle(Graphics g) {

        g.setColor(Color.BLACK);

        g.fillOval(0, 0, getWidth(), getHeight());

    }

    /**
     * Draw a cross in the cell.
     *
     * @param g the graphics
     */

    public void drawCross(Graphics g) {

        g.setColor(Color.BLACK);

        g.drawLine(0, 0, getWidth(), getHeight());

        g.drawLine(0, getHeight(), getWidth(), 0);

    }

    @Override

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (this.player != -1) {

            g.setColor(getColor(this.player));


            if (this.selected) {

                g.fillPolygon(new int[]{getWidth() / 2, getWidth(), getWidth() / 2, 0}, new int[]{0, getHeight() / 2, getHeight(), getHeight() / 2}, 4);

            } else {

                g.fillOval(0, 0, getWidth(), getHeight());

            }

        }

    }

}
