package algorithms.heurestics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

import model.State;
import model.board.Point;
import model.players.Player;

/**
 * <b>
 *      Voronoi is a class that represents the Voronoi heuristic.
 * </b>
 * <p>
 *       The Voronoi heuristic is a heuristic that is based on the Voronoi diagram. It is a heuristic that is 
 *       based on the Voronoi diagram. The Voronoi diagram is a partitioning of a plane into regions based on   
 *       distance to points in a specific subset of the plane.
 * </p>
 * 
 * @author <a href="mailto:22012235@etu.unicaen.fr">Amirath Fara OROU-GUIDOU</a>
 */
public class Voronoi implements Heuristic {

    @Override
    public Map<Player, Integer> getValue(State state) {

        // We create a map that will contain the result of the heuristic
        Map<Player, Integer> result = new ConcurrentHashMap<>();

        // We create a map that will contain the reachable points of each player
        Map<Player, Map<Point, Integer>> reachablePoints = new ConcurrentHashMap<>();
        state.getStillPlayingPlayers().parallelStream().forEach(player -> {
            result.put(player, 0);
            reachablePoints.put(player, this.getDistanceFrom(state.getPlayerHead(player), new ArrayList<>(state.getEmptyPoints())));
        });

        // We traverse the points of the board 
        for (Point point : new ArrayList<>(state.getEmptyPoints())) {

            // We get the closest player to the point
            Player closestPlayer = null;

            // We get the minimum distance between the point and the players
            int minDistance = Integer.MAX_VALUE;
            for (Player player : state.getStillPlayingPlayers()) {

                // If the player can reach the point
                if (reachablePoints.get(player).containsKey(point)) {

                    // If the distance between the point and the player is less than the minimum distance
                    if (reachablePoints.get(player).get(point) < minDistance) {

                        // We update the minimum distance and the closest player
                        minDistance = reachablePoints.get(player).get(point);

                        // We update the closest player
                        closestPlayer = player;
                    
                    } 
                    
                    // Else if the distance between the point and the player is equal to the minimum distance
                    else if (reachablePoints.get(player).get(point) == minDistance) {

                        // We update the closest player
                        closestPlayer = null;
                    }
                }
            }

            // If the closest player is not null
            if (closestPlayer != null) {

                // We increment the value of the closest player and we add it to the result
                result.put(closestPlayer, result.get(closestPlayer) + 1);
            }
        }
        return result;
    }

    /**
     * Returns the map of the distances between the point p and the list of points
     *
     * @param p      the point
     * @param points the list of points
     * @return the map of the distances between the point p and the list of points
     */
    protected Map<Point, Integer> getDistanceFrom(Point p, List<Point> points) {

        Queue<Point> frontier = new LinkedList<>();
        frontier.add(p);
        Map<Point, Integer> distances = new HashMap<>();
        distances.put(p, 0);

        while (!frontier.isEmpty()) {
            Point current = frontier.poll();

            // Neighbors is a list of the 4 neighbors of the current point
            List<Point> neighbors = new ArrayList<>();
            neighbors.add(new Point(current.getX() + 1, current.getY()));
            neighbors.add(new Point(current.getX() - 1, current.getY()));
            neighbors.add(new Point(current.getX(), current.getY() + 1));
            neighbors.add(new Point(current.getX(), current.getY() - 1));

            for (Point neighbor : neighbors) {
                Integer newDistance = distances.get(current) + 1;

                // If the neighbor is in the list of points and the distance is smaller than the current distance
                if (points.contains(neighbor) && newDistance < distances.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    distances.put(neighbor, newDistance);
                    frontier.add(neighbor);
                }
            }
        }
        // All point that are in the list of points and have a distance smaller than Integer.MAX_VALUE
        return distances;
    }

}

