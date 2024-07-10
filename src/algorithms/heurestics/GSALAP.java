package algorithms.heurestics;

import java.util.HashMap;
import java.util.Map;

import model.Move;
import model.State;
import model.players.Player;

/**
 * <b>
 * GSALAP is a class that represents the GSALAP heuristic.
 * </b>
 *
 * <p>
 * The GSALAP heuristic is a heuristic that is used to evaluate the state of the game.
 * It is used to evaluate the state of the game by prioritizing the moves that do not
 * need to change the direction of the snake. So we go as long as possible (GSALAP) in the same direction.
 * </p>
 *
 * @author <a href="mailto:22013393@unicaen.fr">Manne Emile KITSOUKOU</a>
 * @version 1.0
 */
public class GSALAP implements Heuristic {

    @Override
    public Map<Player, Integer> getValue(State state) {

        // We create a map that will contain the result of the heuristic
        Map<Player, Integer> values = new HashMap<>();

        //We go through each player in state get Still Playing Players
        for (Player player : state.getStillPlayingPlayers()) {

            // We initialize the value of the player to 0
            int value = 0;

            // We get the last move of the player
            Move lastMove = state.getLastMove(player);
            // We count the number of moves that do not change the direction of the snake.
            do{
                value++;
                lastMove = lastMove.getArbitraryMove();
            }while(state.getEmptyPoints().contains(lastMove.getHead()));
            values.put(player, value);
        }

        return values;
    }
}
