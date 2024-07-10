package view.ui;

import algorithms.heurestics.Heuristic;
import algorithms.heurestics.Voronoi;
import model.State;
import model.players.Player;
import observer.Listenable;
import observer.Listener;
import view.components.boardGame.CellComponent;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * progress bar which represents the state of the score of the players,
 * that is to say the portion of the board that each player occupies
 */

public class ProgressBar extends JPanel implements Listener {

    /**
     * The state that the progress bar represents.
     */
    private final State state;

    /**
     * The heurestic that permit to evaluate the state.
     */
    private final Heuristic heuristic = new Voronoi();

    /**
     * Builds a new progress bar with the given state.
     *
     * @param state the state that the progress bar represents
     */
    public ProgressBar(State state) {
        this.state = state;
        this.setBorder(BorderFactory.createLoweredBevelBorder());
        (this.state).addListener(this);

    }

    /**
     * Returns the state that the progress bar represents.
     *
     * @return the state that the progress bar represents
     */
    public State getState() {
        return this.state;
    }

    @Override
    public void notify(Listenable listenable, String s, Object... objects) {
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Map<Player, Integer> values = this.heuristic.getValue(this.state);
        int total = values.values().stream().mapToInt(Integer::intValue).sum();
        int x = 0;
        for (Player player : values.keySet()) {
            g.setColor(CellComponent.getColor(state.getPlayers().indexOf(player)));
            g.fillRect(x, 0, (int) ((double) values.get(player) / (double) total * (double) this.getWidth()), this.getHeight());
            x += (int) ((double) values.get(player) / (double) total * (double) this.getWidth());
            // Write the name of the player in the middle of the bar in the bottom of the bar
            g.setColor(Color.WHITE);
            //g.drawString(player.getName(), x - (int) ((double) values.get(player) / (double) total * (double) this.getWidth()) / 2, this.getHeight() / 2);
            g.drawString(player.getName(), x - (int) ((double) values.get(player) / (double) total * (double) this.getWidth()) / 2, this.getHeight() - 5);

        }
    }


}
