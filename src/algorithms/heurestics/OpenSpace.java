package algorithms.heurestics;

import java.util.HashMap;
import java.util.Map;

import model.State;
import model.players.Player;

/**
 * <b>
 * OpenSpace is a class that represents the open space heuristic.
 * </b>
 *
 * <p>
 * The open space heuristic is a heuristic that is used to evaluate the state of the game.
 * It is used to evaluate the state of the game by counting the number of empty points that are
 * adjacent to the head of the player.
 * </p>
 *
 * @author <a href="mailto:22013393@unicaen.fr>Manne Emile KITSOUKOU</a>
 * @version 1.0
 */
public class OpenSpace implements Heuristic {

    @Override
    public Map<Player, Integer> getValue(State state) {

        // Initialize the map that will contain the result of the heuristic
        Map<Player, Integer> values = new HashMap<>();
        state.getStillPlayingPlayers().parallelStream().forEach(p -> values.put(p, state.getValidMoves(p).size()));
        
        return values;
    }
}
