package algorithms.adversarial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import algorithms.heurestics.Heuristic;
import application.Constante;
import model.Move;
import model.State;
import model.players.Player;

/**
 * <b>
 *     Adversarial search is an abstract class that represent the adversarial search algorithm.
 * </b>
 *
 * <p>
 *     An adversarial search algorithm is an algorithm that is used to solve a game. It is used to find the best move
 *     for a player in a game. It is a tree search algorithm were each node of the tree is a state of the game. For choosing
 *     the best move, the algorithm uses a Heuristic function that evaluate the state of the game.
 * </p>
 *
 * @author <a href="mailto:22013393@unicaen.fr>Manne Emile KITSOUKOU</a>
 * @version 1.0
 */
public abstract class AdversarialSearch {

    /**
     * The number of nodes explored by the algorithm.
     */
    private long nodesExplored;

    /**
     * The maximum depth of the search.
     */
    private final int maxDepth;


    /**
     * The boolean indicating if we use shallow pruning.
     */
    private final boolean shallowPruning;

    /**
     * The player that want to play.
     */
    private final Player player;

    /**
     * The Heuristic function used by the algorithm.
     */
    private final Heuristic heuristicFunction;


    /**
     * <b>
     *     Protected constructor of the class.
     * </b>
     *
     * <p>
     *     This constructor is used to initialize the maximum depth of the search and the number of nodes explored.
     * </p>
     *
     * @param maxDepth the maximum depth of the search
     * @param shallowPruning the boolean indicating if we use shallow pruning
     * @param player the player that want to play
     * @param heuristicFunction the heuristic function used by the algorithm
     * @param selectionAlgorithm the selection algorithm used by the algorithm
     */
    protected AdversarialSearch(int maxDepth, boolean shallowPruning, Player player, Heuristic heuristicFunction) {

        this.player = player;
        this.maxDepth = maxDepth;
        this.shallowPruning = shallowPruning;
        this.nodesExplored = 0;
        this.heuristicFunction = heuristicFunction;
    }


    /**
     * <b>
     *     Protected constructor of the class.
     * </b>
     *
     * <p>
     *     This constructor is used to initialize the maximum depth of the search and the number of nodes explored.
     * </p>
     *
     * @param maxDepth the maximum depth of the search
     * @param player the player that want to play
     * @param heuristicFunction the heuristic function used by the algorithm
     * @param selectionAlgorithm the selection algorithm used by the algorithm
     */
    protected AdversarialSearch(int maxDepth, Player player, Heuristic heuristicFunction) {
        this(maxDepth,  false, player, heuristicFunction);
    }


    /**
     * Returns the maximum depth of the search.
     * @return the maximum depth of the search.
     */
    public int getMaxDepth() {
        return this.maxDepth;
    }


    /**
     * Returns the number of nodes explored by the algorithm.
     * @return the number of nodes explored by the algorithm.
     */
    public long getNodesExplored() {
        return this.nodesExplored;
    }

    /**
     * Returns the player that want to play.
     * @return the player that want to play.
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Returns the heuristic function used by the algorithm.
     * @return the heuristic function used by the algorithm.
     */
    public Heuristic getHeuristicFunction() {
        return this.heuristicFunction;
    }


    /**
     * Returns the boolean indicating if we use shallow pruning.
     * @return the boolean indicating if we use shallow pruning.
     */
    public boolean isShallowPruning() {
        return this.shallowPruning;
    }

    /**
     * Increments the number of nodes explored by the algorithm.
     */
    protected void incrementNodesExplored() {
        this.nodesExplored++;
    }

    /**
     * Resets the number of nodes explored by the algorithm.
     */
    protected void resetNodesExplored() {
        this.nodesExplored = 0;
    }

    /**
     * Returns the best move for the player in the state.
     *
     * @param state the state of the game
     * @return the best move for the player in the state
     */
    public Move bestMove(State state){

        List<Player> playersNotYetPlayed = new ArrayList<>(state.getPlayers());
        playersNotYetPlayed.remove(this.getPlayer());

        Move bestMove = null;
        Double bestScore = null;

        Map<State, Object[]> cache = new HashMap<>();

        for(Move move : state.getValidMoves(player)){

            Map<Player, Move> moves = new HashMap<>();
            moves.put(this.getPlayer(), move);

            Map<Player, Double> score = this.valueOfState(state, playersNotYetPlayed, moves, cache, this.getMaxDepth(), 1.0, System.currentTimeMillis());

            if(bestScore == null || score.get(this.getPlayer()) > bestScore){
                bestScore = score.get(this.getPlayer());
                bestMove = move;
            }

            // Pruning
            if(bestScore >= 1.0){
                break;
            }
        }

        return bestMove == null ? state.getLastMove(player).getArbitraryMove() : bestMove;
    }

    /**
     * Returns the best score for the player in the state.
     *
     * @param state the state of the game
     * @param playersNotYetPlayed the players who have not chosen their action yet
     * @param moves the moves of the players who have already chosen their action
     * @param cache the cache of the algorithm
     * @param depth the depth of the search
     * @param maxBound the maximum bound of the search
     * @param startTimestamp the timestamp of the start of the search
     */
    public Map<Player, Double> valueOfState(State state, List<Player> playersNotYetPlayed, Map<Player, Move> moves, Map<State, Object[]> cache, int depth, double maxBound, long startTimestamp) {

        // If the state is final, ie the game is over, we evaluate the state like a final state
        if (state.isOver()){
            return this.evaluateFinalState(state);
        }


        // If the state is not final, but we reached the maximum depth of the search, we evaluate the state like a middle game state
        if (depth <= 0  || (!Constante.isTimeLimitMaxValue() && System.currentTimeMillis() - startTimestamp > Constante.getTimeLimit())) {
            return this.evaluateMidGameState(state);
        }

        // If all players have chosen their action, we can play the game
        if (playersNotYetPlayed.isEmpty()) {
            State nextState = state.copy();
            nextState.play(moves);

            // We try to get the value of the state from the cache
            Object[] cacheValue = cache.getOrDefault(nextState, null);
            // If the value is not null, we return it
            if (cacheValue != null) {
                // We get the depth of the state in the cache
                int cacheDepth = (int) cacheValue[0];
                // If the depth of the state in the cache is greater than the depth of the search, we return the value of the state in the cache
                if (cacheDepth >= depth) {
                    return (Map<Player, Double>) cacheValue[1];
                }
            }

            // List of players who have not played yet
            playersNotYetPlayed = new ArrayList<>(nextState.getStillPlayingPlayers());
            boolean isPresent = playersNotYetPlayed.remove(this.getPlayer());
            if (isPresent) {
                // We had the player in the list of players in the first position
                playersNotYetPlayed.add(0, this.getPlayer());
            }

            Map<Player, Double> score = this.valueOfState(nextState, playersNotYetPlayed, new HashMap<>(), cache, depth - 1, 1.0, startTimestamp);
            // We increment the number of nodes explored
            this.incrementNodesExplored();
            // We update the cache
            cache.put(nextState, new Object[]{depth, score});
            return score;

        }

        // we use the selection algorithm to get the best score for the player in the state
        return this.bestScore(state, moves, playersNotYetPlayed, cache, depth, maxBound, startTimestamp);

    }

    /**
     * Returns the best score for the player in the state.
     *
     * @param state the state of the game
     * @param moves the moves of the players who have already chosen their action
     * @param playersNotYetPlayed the players who have not chosen their action yet
     * @param cache the cache of the algorithm
     * @param depth the depth of the search
     * @param maxBound the maximum bound of the search
     * @param startTimestamp the timestamp of the start of the search
     */
    protected abstract Map<Player, Double> bestScore(State state, Map<Player, Move> moves, List<Player> playersNotYetPlayed, Map<State, Object[]> cache, int depth, double maxBound, long startTimestamp);
    

    /**
     * Evaluates the state like a final state.
     *
     * @param state the state of the game
     * @return the score of the state
     */
    private Map<Player, Double> evaluateFinalState(State state){

        Player winner = state.getWinner();

        Map<Player, Double> score = new HashMap<>();

        if(winner == null){
            state.getPlayers().forEach(p -> score.put(p, 0.0));
        }else{
            state.getPlayers().forEach(p -> score.put(p, player.equals(winner) ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY));
        }

        return score;

    }

    /**
     * Evaluates the state like a not final state(middle game).
     *
     * @param state the state of the game
     * @return the score of the state
     */
    private Map<Player, Double> evaluateMidGameState(State state){

        // We get the evaluation of the state
        Map<Player, Integer> evaluation = this.getHeuristicFunction().getValue(state);

        // We get the sum of the evaluation of the state
        int sum = evaluation.values().stream().mapToInt(Integer::intValue).sum();

    
        Map<Player, Double> value = new HashMap<>();

        // If the sum is 0, we return a score of 0 for all players
        state.getPlayers().forEach(p -> value.put(p, Double.NEGATIVE_INFINITY));

        // We get the score of the state
        state.getStillPlayingPlayers().forEach(p -> value.put(p, (double) evaluation.get(p) / sum));


        return value;
    }


}
