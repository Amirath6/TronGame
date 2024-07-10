package algorithms.adversarial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import algorithms.heurestics.Heuristic;
import model.Move;
import model.State;
import model.players.Player;

/**
 * <b>
 *      Paranoid is a class that implements the Paranoid algorithm.
 * </b>
 * <p>
 *      The paranoid algorithm does this by reducing an n-player game to a 2-player game. It assumes that a coalition of n-1
 *      players has formed to play against the remaining player
 * </p>
 *
 * @author <a href="mailto:22012235@etu.unicaen.fr">Amirath Fara OROU-GUIDOU</a>
 * @version 1.0
 */
public class Paranoid extends AdversarialSearch {

    /**
     * Constructs a Paranoid object with the given parameters
     * @param maxDepth The maximum depth of the search
     * @param shallowPruning A boolean that indicates if we use shallow pruning or not
     * @param player The player that we want to maximize the score
     * @param heuristicFunction The heuristic function that we use to evaluate the states
     */
    public Paranoid(int maxDepth, boolean shallowPruning, Player player, Heuristic heuristicFunction) {
        super(maxDepth, shallowPruning, player, heuristicFunction);
    }

    @Override
    protected Map<Player, Double> bestScore(State state, Map<Player, Move> moves, List<Player> playersNotYetPlayed,
            Map<State, Object[]> cache, int depth, double maxBound, long startTimestamp) {

        
        // We remove the current player from the list of players not yet played
        List<Player> newPlayingPlayers = new ArrayList<>(playersNotYetPlayed);
        Player currentPlayer = newPlayingPlayers.remove(0);
        
        // We get the list of moves of the current player
        List<Move> movesOfCurrentPlayer = state.getValidMoves(currentPlayer);
        
        Move currentMove = null;
        Map<Player, Move> newMoves = null;
        
        // We get an iterator over the moves of the current player
        Iterator<Move> iterator = movesOfCurrentPlayer.iterator();
        
        // If the current player has no move, we get the arbitrary move
        if(!iterator.hasNext()) {
            currentMove = state.getLastMove(currentPlayer).getArbitraryMove();
        } else {
            currentMove = iterator.next();
        }

        // We get our first best values
        newMoves = new HashMap<>(moves);

        // We add the current move to the moves
        newMoves.put(currentPlayer, currentMove);

        // We get the best values of the current state
        Map<Player, Double> bestValues =  this.valueOfState(state, newPlayingPlayers, newMoves, cache, depth, maxBound, startTimestamp);

        // We iterate over the moves of the current player
        while(iterator.hasNext()) {
            currentMove = iterator.next();
            newMoves = new HashMap<>(moves);
            newMoves.put(currentPlayer, currentMove);

            // The bound for others players is the 1 - best value of the current player if the current player is the maximizing player others the maxBound does not change
            Double bound = currentPlayer.equals(this.getPlayer()) ? 1.0 - (bestValues.get(currentPlayer) < 0 ? 0 : bestValues.get(currentPlayer)) : maxBound;

            Map<Player, Double> currentValues = this.valueOfState(state, newPlayingPlayers, newMoves, cache, depth, bound, startTimestamp);
            
            // If the current player is the maximizing player
            if(currentPlayer.equals(this.getPlayer())) {
                // We maximize the value of the current player
                if(currentValues.get(currentPlayer) > bestValues.get(currentPlayer) || (currentValues.get(currentPlayer).equals(bestValues.get(currentPlayer)) && currentValues.get(currentPlayer) == 1.0)) {
                    bestValues = currentValues;
                }

                // If we are in a shallow pruning mode
                if(this.isShallowPruning() && bestValues.get(currentPlayer) >= maxBound) {
                    break;
                }

            } else {

                // We minimize the value of the current player
                if(currentValues.get(currentPlayer) < bestValues.get(currentPlayer)) {
                    bestValues = currentValues;
                }

                // If we are in a shallow pruning mode
                if(this.isShallowPruning() && bestValues.get(currentPlayer) <= maxBound) {
                    break;
                }
            }

        }

        return bestValues;
    }

    
    
}
