package model.players;

import model.Move;
import model.State;


/**
 * <b>
 * A player is an entity that can play the game.
 * </b>
 *
 * <p>
 * In a game, a player is an agent that can impact the progression of the game by
 * interacting with the game. A player can be a human or a computer.
 * </p>
 *
 * @author <a href="mailto:22013393@unicaen.fr>Manne Emile KITSOUKOU </a>
 * @version 1.0
 */
public interface Player {

    /**
     * <b>
     * Returns the name of the player.
     * </b>
     *
     * @retun the name of the player
     */
    String getName();

    /**
     * <b>
     * Choose a move to perform in a state
     * </b>
     *
     * <p>
     * A move is a specific point in a board in which the player want to play(put a symbol)
     * </p>
     *
     * @param state the state in which the player want to play
     * @return the move to perform
     */
    Move chooseMove(State state);

    /**
     * <b>
     * Returns the number of nodes explored by the player.
     * </b>
     *
     * @return the number of nodes explored by the player
     */
    default long getNodesExplored() {
        return 0;
    }
}
