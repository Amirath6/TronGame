package model.players;

import algorithms.adversarial.AdversarialSearch;
import algorithms.heurestics.Heuristic;
import model.Move;
import model.State;

/**
 * <b>
 *     SmartPlayer is a class that represents a smart player.   
 * </b>
 * 
 * <p>
 *    A smart player is a player that uses an adversarial search algorithm to choose the best move.
 * </p>
 * 
 * @author <a href="mailto:22013393@unicaen.fr">Manne Emile KITSOUKOU</a>
 * @version 1.0
 */

public class SmartPlayer implements Player {

    /*
     * Attributes of the class
     */

    // The name of the player
    private final String name;

    // The adversarial search algorithm that will be used to choose the best move.
    private AdversarialSearch algorithm;

    /**
     * <b>
     *     Constructor of the class.
     * </b>
     * 
     * <p>
     *      The constructor of the class SmartPlayer.
     * </p>
     * 
     * @param name The name of the player.
     * @param algorithm The adversarial search algorithm that will be used to choose the best move.
     */
    public SmartPlayer(String name, AdversarialSearch algorithm) {
        this.name = name;
        this.algorithm = algorithm;
    }

    /**
     * <b>
     *    Second Constructor of the class.
     * </b>
     * 
     * <p>
     *    The second constructor of the class SmartPlayer.
     * </p>
     * 
     * @param name
     * @param algorithmName
     * @param heuristicName
     * @param maxDepth
     * @param shallowPruning
     */
    public SmartPlayer(String name, String algorithmName, String heuristicName, int maxDepth, boolean shallowPruning) {
        this.name = name;

        // If the name of the algorithm is not begin with the name of package, we add it.
        if (!algorithmName.startsWith("algorithms.adversarial.")) {
            algorithmName = "algorithms.adversarial." + algorithmName;
        }

        // if the name of the heuristic is not begin with the name of package, we add it.
        if (!heuristicName.startsWith("algorithms.heurestics.")) {
            heuristicName = "algorithms.heurestics." + heuristicName;
        }

        // Try to create an instance of the heuristic function.
        Heuristic heuristicFunction = null;
        try {
            Class<?> heuristicClass = Class.forName(heuristicName);
            heuristicFunction = (Heuristic) heuristicClass.getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Class<?> algorithmClass = Class.forName(algorithmName);
            this.algorithm = (AdversarialSearch) algorithmClass.getConstructor(int.class, boolean.class, Player.class, Heuristic.class).newInstance(maxDepth, shallowPruning, this, heuristicFunction);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * <b>
     *     Third Constructor of the class.
     * </b>
     * 
     * <p>
     *     The third constructor of the class SmartPlayer.
     * </p>
     * 
     * @param name
     * @param algorithmName
     * @param maxDepth
     * @param shallowPruning
     */
    public SmartPlayer(String name, String algorithmName, int maxDepth, boolean shallowPruning) {
        this(name, algorithmName, "algorithms.heurestics.Checker", maxDepth, shallowPruning);
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
        return this.name;
    }

    /**
     * Returns the adversarial search algorithm that will be used to choose the best move.
     * 
     * @return The adversarial search algorithm that will be used to choose the best move.
     */
    public AdversarialSearch getAlgorithm() {
        return algorithm;
    }

    /**
     * <b>
     *    Choose the best move.
     * </b>
     * 
     * <p>
     *   The method that choose the best move.
     * </p>
     * 
     * @param state The current state of the game.
     * @return The best move based on the adversarial search algorithm.
     */
    @Override
    public Move chooseMove(State state) {
        return algorithm.bestMove(state);
    }

    /**
     * <b>
     *    Getter of the number of nodes explored.
     * </b>
     * 
     * <p>
     *   The getter of the number of nodes explored.
     * </p>
     * 
     * @return The number of nodes explored by the algorithm.
     */
    @Override
    public long getNodesExplored() {
        return algorithm.getNodesExplored();
    }

    /**
     * <b>
     *     To string method.
     * </b>
     * 
     * <p>
     *    The to string method of the class SmartPlayer.
     * </p>
     * 
     * @return The string representation of the class SmartPlayer.
     */
    @Override
    public String toString() {
        return "SmartPlayer{" +
                "name='" + name + '\'' +
                ", algorithm=" + algorithm +
                '}';
    }

    /**
     * <b>
     *    Equals method.
     * </b>
     * 
     * <p>
     *   The equals method of the class SmartPlayer. Two smart players are equals if they have the same name.
     * </p>
     * 
     * @param obj The object to compare.
     * @return True if the two objects are equals, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SmartPlayer) {
            SmartPlayer other = (SmartPlayer) obj;
            return this.name.equals(other.name);
        }
        return false;
    }

    /**
     * <b>
     *   Hash code method.
     * </b>
     * 
     * <p>
     *  The hash code method of the class SmartPlayer.
     * </p>
     * 
     * @return The hash code of the object (here is the name).
     */
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }


}
