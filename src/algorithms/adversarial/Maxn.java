package algorithms.adversarial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import algorithms.heurestics.Heuristic;
import application.Constante;
import model.Move;
import model.State;
import model.players.Player;

/**
 * <b>
 *     Maxn is a class that implements the Maxn algorithm.
 * </b>
 * <p>
 *    The Maxn algorithm is a minimax algorithm that uses a heuristic function to evaluate the state of the game.
 * </p>
 * 
 * @author <a href="mailto:22012235@etu.unicaen.fr>Amirath Fara OROU-GUIDOU</a>
 * @version 1.0
*/
public class Maxn extends AdversarialSearch {

    public Maxn(int maxDepth, boolean shallowPruning, Player player, Heuristic heuristicFunction) {
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
                
                Iterator<Move> iterator = movesOfCurrentPlayer.iterator();
                
                // If the current player has no move, we get the arbitrary move
                if(!iterator.hasNext()) {
                    currentMove = state.getLastMove(currentPlayer).getArbitraryMove();
                } else {
                    currentMove = iterator.next();
                }
                
                // We get our first best values
                newMoves = new HashMap<>(moves);
                newMoves.put(currentPlayer, currentMove);
                Map<Player, Double> bestValues =  this.valueOfState(state, newPlayingPlayers, newMoves, cache, depth, maxBound, startTimestamp);

                // We iterate over the moves of the current player
                while(iterator.hasNext()) {
                    currentMove = iterator.next();
                    newMoves = new HashMap<>(moves);
                    newMoves.put(currentPlayer, currentMove);

                    // the bound for others players is obtained by remove the best value of the current player from the maxBound
                    double bestCurrentPlayerValue = bestValues.get(currentPlayer);
                    double bound = maxBound - (bestCurrentPlayerValue < 0 ? 0 : bestCurrentPlayerValue);

                    Map<Player, Double> newValues = this.valueOfState(state, newPlayingPlayers, newMoves, cache, depth, bound, startTimestamp);
                    
                    // We maximize the value of the current player
                    if(newValues.get(currentPlayer) > bestValues.get(currentPlayer) || (newValues.get(currentPlayer).equals(bestValues.get(currentPlayer)) && Constante.RANDOM.nextBoolean())) {
                        bestValues = newValues;
                    }

                    // We prune if the current player has a value greater than the bound
                    if(this.isShallowPruning() && bestValues.get(currentPlayer) >= maxBound) {
                        return bestValues;
                    }
                }

                return bestValues;
        
    }



    
    
}
