package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import model.Move;
import model.State;
import model.players.Player;
import model.players.SmartPlayer;

/**
* <b>
* Executable class for running experiments.
* </b>
*
* <p>
* This class is used to run experiments on the game. An experiment is a set of games played
* between two players. The players are chosen by the user, and the number of games to be played.
* The results of the experiment are printed to the console.
* </p>
*
* @author <a href="mailto:22013393@unicaen.fr">Manne Emile KITSOUKOU</a>
* @version 1.0
*/
public class Experiment {

   /**
    * <b>
    * Main method of the class.
    * </b>
    *
    * <p>
    * This method is used to run experiments on the game. An experiment is a set of games played
    * between two players. The players are chosen by the user, and the number of games to be played.
    * The results of the experiment are printed to the console.
    * </p>
    *
    * @param args The command line arguments.
    */
   public static void main(String[] args) {

       // The first argument is the size of the board.
       int boardSize;
       if (args.length < 1) {
           System.err.println("Usage: java core.Experiment <boardSize>");
           return;
       } else {
           // We try to parse the first argument as an integer and check if it is positive.
           try {
               boardSize = Integer.parseInt(args[0]);
           } catch (NumberFormatException e) {
               System.err.println("The board size must be an integer.");
               return;
           }
       }

       // The remaining arguments are the name classes of the players and the depth of the search
       if (args.length < 2) {
           System.err.println("Usage: java core.Experiment <boardSize> <player1> <player2> ... <playerN>");
           System.err.println("Where <playerX> is the information about the player X : algorithm, strategy, depth, coalitionSize, pruning");
           System.exit(1);
       }

       // We create the players.
       List<Player> players = new ArrayList<>();

       for (int i = 1; i < args.length; i++) {
           String[] playerInfo = args[i].split(",");
           if (playerInfo.length != 5) {
               System.err.println("The information about the player " + i + " is not correct.");
               System.err.println("Usage: java core.Experiment <boardSize> <player1> <player2> ... <playerN>");
               System.err.println("Where <playerX> is the information about the player X : algorithm, strategy, depth, coalitionSize, pruning");
               System.exit(1);
           }

           // We create the player.
           Player player = new SmartPlayer("Player" + i, playerInfo[0], playerInfo[1], Integer.parseInt(playerInfo[2]), Boolean.parseBoolean(playerInfo[3]));
           players.add(player);
       }


       // We create the state.
       State state = new State(boardSize, players);
       int nbGames = 0;
       while (!state.isOver()) {
            Map<Player, Move> moves = new ConcurrentHashMap<>();

           
           state.getPlayers().parallelStream().forEach(player -> moves.put(player, player.chooseMove(state)));

           state.play(moves);
           nbGames++;
       }

       // Get the winner of the game.
       Player winner = state.getWinner();

       // Results for the first player.
       SmartPlayer player1 = (SmartPlayer) players.get(0);

       // -1 the player1 are looser, 0 they have draw, 1 the player1 are winner
       int result = winner == null ? 0 : (winner.equals(player1) ? 1 : -1);

       // We print the results.
       System.out.println("Results for " + player1.getName() + " (loose=-1, draw=0, win=1): " + result);
       System.out.println("Number of explored states: " + player1.getNodesExplored());
       System.out.println("Number of games played: " + nbGames);
       System.out.println("Profondeur de recherche: " + player1.getAlgorithm().getMaxDepth());
         System.out.println("Taille de Grid: " + boardSize);





   }
}
