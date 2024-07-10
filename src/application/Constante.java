package application;

import java.util.Random;

/**
 * Class that contains all the constants of the application.
 *
 * @version 1.0
 */

public final class Constante {

    /**
     * The random number generator.
     */
    public static final Random RANDOM = new Random();

    /**
     * The time limit for the algorithm.
     */
    private static long timeLimit = Long.MAX_VALUE;


    /**
     * Returns the time limit for the algorithm.
     */
    public static long getTimeLimit() {
        return timeLimit;
    }

    /**
     * Sets the time limit for the algorithm.
     */
    public static void setTimeLimit(long timeLimit) {
        Constante.timeLimit = timeLimit;
    }

    /**
     * Checks if the time limit is max value.
     * @return true if the time limit is max value, false otherwise.
     */
    public static boolean isTimeLimitMaxValue() {
        return timeLimit == Long.MAX_VALUE;
    }


}