package algorithms.heurestics;

import model.State;
import model.players.Player;

import java.util.Map;

public interface Heuristic {


    /**
     * <b>
     * Returns the value of the heuristic function for a state.
     * </b>
     *
     * @param state the state for which we want to evaluate the heuristic function
     * @return the value of the heuristic function for a state
     */
    Map<Player, Integer> getValue(State state);
}
