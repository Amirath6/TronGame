package model.players;

import java.util.List;

import model.Move;
import model.State;

/**
 * <b>
 *      RandomPlayer is a class that represents a random player.
 * </b>
 * 
 * <p>
 *     A random player is a player that chooses a move randomly.
 * </p>
 * 
 * @author <a href="mailto:22013393@unicaen.fr">Manne Emile KITSOUKOU</a>
 * @version 1.0
 */
public class RandomPlayer implements Player {

    /*
     * Attributes of the class
     * The name of the player
     */
    private final String name;

    /**
     * <b>
     *      Constructor of the class.
     * </b>
     * 
     * <p>
     *     The constructor of the class RandomPlayer.
     * </p>
     * 
     * @param name The name of the player.
     */
    public RandomPlayer(String name) {
        this.name = name;
    }


    /**
     * <b>
     *      Getter of the name of the player.
     * </b>
     * 
     * <p>
     *     The getter of the name of the player.
     * </p>
     * 
     * @return The name of the player.
     */
    @Override
    public String getName() {
        return name;
    }


    /**
     * <b>
     *      Choose a move randomly.
     * </b>
     * 
     * <p>
     *     The method chooseMove chooses a move randomly.
     * </p>
     * 
     * @param state The state of the game.
     * @return The move chosen randomly.
     */
    @Override
    public Move chooseMove(State state) {

        // We get the list of available moves
        List<Move> availableMoves = state.getValidMoves(this);

        // If there is no available move, we return the last move of the player
        if(availableMoves.isEmpty()) {
            return state.getLastMove(this).getArbitraryMove();
        }

        // We return a random move
        return availableMoves.get((int) (Math.random() * availableMoves.size()));
    }

    /**
     * <b>
     *      The method toString returns the name of the player.
     * </b>
     * 
     * <p>
     *     The method toString returns the name of the player.
     * </p>
     * 
     * @return The name of the player.
     */
    @Override
    public String toString() {
        return name;
    }
}
