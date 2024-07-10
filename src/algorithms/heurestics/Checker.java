package algorithms.heurestics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import model.State;
import model.board.Point;
import model.players.Player;


/**
 * <b>
 *     Checker is a class that implements the Checker heuristic.
 * </b>
 * <p>
 *      The Checker heuristic is a heuristic that is based on the Voronoi diagram. It is a heuristic that is based on the
 *      Voronoi diagram. The Voronoi diagram is a partitioning of a plane into regions based on distance to points in a specific subset of
 *      the plane.
 * </p>
 * 
 * @author <a href="mailto:22012235@etu.unicaen.fr">Amirath Fara OROU-GUIDOU</a>
 * @version 1.0
 */
public class Checker extends Voronoi {

    @Override
    public Map<Player, Integer> getValue(State state) {

        // We create a map that will contain the result of the heuristic
        Map<Player, Integer> result = new ConcurrentHashMap<>();

        // We create a map that will contain the reachable points of each player
        Map<Player, Map<Point, Integer>> reachablePoints = new ConcurrentHashMap<>();

        // We create a map that will contain the number of points of each player
        Map<Player, List<Integer>> checkerBoard = new ConcurrentHashMap<>();

        // We create a map that will contain the voronoi diagram of each player
        Map<Player, Set<Point>> playerVoronoi = new ConcurrentHashMap<>();

        // We initialize the maps
        state.getStillPlayingPlayers().parallelStream().forEach(player -> {

            // We add the player and its score to the result
            result.put(player, 0);

            // We add the player to the voronoi diagram
            playerVoronoi.put(player, new HashSet<>());

            // We add the player to the checker board
            checkerBoard.put(player, new ArrayList<>());

            //
            checkerBoard.get(player).add(0);
            checkerBoard.get(player).add(0);

        
            reachablePoints.put(player, this.getDistanceFrom(state.getPlayerHead(player), state.getEmptyPoints()));
        });


        // We compute the voronoi diagram
        state.getEmptyPoints().parallelStream().forEach(point ->{
            // We get the closest player
            Player closestPlayer = null;

            // We initialize the minimum distance to the maximum value of an integer  
            int minDistance = Integer.MAX_VALUE;

            // We go through each player in state get Still Playing Players
            for (Player player : state.getStillPlayingPlayers()) {

                // If the player has a reachable point
                if (reachablePoints.get(player).containsKey(point)) {

                    // If the distance of the point to the player is less than the minimum distance
                    if (reachablePoints.get(player).get(point) < minDistance) {

                        // We update the minimum distance
                        minDistance = reachablePoints.get(player).get(point);

                        // We update the closest player
                        closestPlayer = player;
                    } 
                    // Else If the distance of the point to the player is equal to the minimum distance
                    else if (reachablePoints.get(player).get(point) == minDistance) {

                        // We update the closest player to null
                        closestPlayer = null;
                    }
                }
            }

            // If the closest player is not null
            if (closestPlayer != null) {
                
                // If the sum of the x and y coordinates of the point modulo 2 is equal to 0
                if((point.getX() + point.getY()) % 2 == 0){

                    // We increment the number of points of the closest player on the checker board
                    checkerBoard.get(closestPlayer).set(0, checkerBoard.get(closestPlayer).get(0) + 1);

               }else{
                
                    // We increment the number of points of the closest player on the checker board
                    checkerBoard.get(closestPlayer).set(1, checkerBoard.get(closestPlayer).get(1) + 1);
               }

                // We add the point to the voronoi diagram of the closest player
                playerVoronoi.get(closestPlayer).add(point);

            }
        });

        //We go through each player in state get Still Playing Players
        for (Player player : state.getStillPlayingPlayers()) {

            // We compute the difference between the number of points of the player on the checker board and the number of points of the player on the voronoi diagram
            int diff = Math.abs(checkerBoard.get(player).get(0) - checkerBoard.get(player).get(1));
            // We add player and the difference between the size of the voronoi diagram to the result
            result.put(player, playerVoronoi.get(player).size() - diff);
       }
        return result;
    }

}

