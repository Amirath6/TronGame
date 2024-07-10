package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import model.board.Board;
import model.board.Point;
import model.players.Player;
import observer.ListenableObject;

/**
 * <b>
 * <p>
 * State is a class that represent a tron game
 *
 * </b>
 *
 *
 *
 * <p>
 * <p>
 * A tron game is a multiplayer game in which each player play against.
 * <p>
 * In this game, each player is represented by a symbol. The goal of the game
 * <p>
 * is to make the other player crash into a symbol of the other player or into a wall.
 *
 * </p>
 *
 *
 *
 * <p>
 * <p>
 * The game is played on a board. The board is a two-dimensional grid. Each cell of the grid
 * <p>
 * can be empty or occupied by a symbol. The game is played by two or more players. A player can
 * <p>
 * only move in four(4) directions: up, down, left and right. A player can't move in a direction
 * <p>
 * that is opposite to the direction in which he is moving.
 * <p>
 * A player can't move in a direction that is occupied by a symbol of the same or other player.
 * <p>
 * A player can't move in a direction that is occupied by a wall.
 *
 * </p>
 *
 * @author <a href="mailto:22013393@unicaen.fr>Manne Emile KITSOUKOU</a>
 * @version 1.0
 */

public class State extends ListenableObject {

    /**
     * The board of the game.
     */

    private final Board board;

    /**
     * The list of players of the game.
     */

    private final List<Player> players;

    /**
     * The list of still playing players.
     */

    private final List<Player> stillPlayingPlayers;

    /**
     * The map that associate each player to the list of his moves.
     */

    private final Map<Player, List<Move>> playerMoves;

    /**
     * The list of points that are not occupied by a symbol.
     */

    private final List<Point> emptyPoints;


    /**
     * <b>
     * <p>
     * Creates a new game with the specified parameters.
     *
     * </b>
     *
     * @param board               the board of the game.
     * @param players             the list of players of the game.
     * @param stillPlayingPlayers the list of still playing players.
     * @param playerMoves         the map that associate each player to the list of his point.
     * @throws IllegalArgumentException if the board is null
     * @throws IllegalArgumentException if the list of players is null
     * @throws IllegalArgumentException if the list of players is empty
     * @throws IllegalArgumentException if the list of still playing players is null
     * @throws IllegalArgumentException if the map that associate each player to the list of his point is null
     */

    public State(Board board, List<Player> players, List<Player> stillPlayingPlayers, List<Point> emptyPoints, Map<Player, List<Move>> playerMoves) {
        super("playMoves", "movePlayer");
        if (board == null) {

            throw new IllegalArgumentException("The board is null");

        }

        if (players == null) {

            throw new IllegalArgumentException("The list of players is null");

        }

       if (players.isEmpty()) {

           throw new IllegalArgumentException("The list of players is empty");

       }

        if (stillPlayingPlayers == null) {

            throw new IllegalArgumentException("The list of still playing players is null");

        }

        if (playerMoves == null) {

            throw new IllegalArgumentException("The map that associate each player to the list of his moves is null");

        }

        this.board = board;

        this.players = players;

        this.emptyPoints = emptyPoints;

        this.stillPlayingPlayers = stillPlayingPlayers;

        this.playerMoves = playerMoves;

    }

    /**
     * <b>
     * <p>
     * Creates a new game with the size of the board and the list of players given in parameters
     *
     * </b>
     *
     * @param size    the size of the board
     * @param players the list of players of the game
     */

    public State(int size, List<Player> players) {

        this(new Board(size), players, new ArrayList<>(players), new ArrayList<>(), new HashMap<>());

        this.initBeginPoints();

    }

    /**
     * <b>
     * <p>
     * Initializes the begin point of each player
     *
     * </b>
     *
     *
     *
     * <p>
     * <p>
     * The begin point of each player is at distance (size / 2) from the center of the board.
     * <p>
     * Each player is placed at a different side of the board.
     *
     * </p>
     */

    private void initBeginPoints() {

        int size = this.board.getSize();

        if (this.players.size() == 2) {

            List<Move> pointsPlayer1 = new ArrayList<>();

            List<Move> pointsPlayer2 = new ArrayList<>();

            pointsPlayer1.add(new Move(new Point(0, 0)));

            pointsPlayer2.add(new Move(new Point(size - 1, size - 1)));

            this.playerMoves.put(this.players.get(0), pointsPlayer1);

            this.playerMoves.put(this.players.get(1), pointsPlayer2);

            this.board.setCell(new Point(0, 0), 0);

            this.board.setCell(new Point(size - 1, size - 1), 1);

        } else {

            int distance = (size / 2) - 1;

            int centerX = size / 2;

            int centerY = size / 2;

            int nbPlayers = this.players.size();

            for (int i = 0; i < this.players.size(); i++) {

                Player player = this.players.get(i);

                int x = (int) (centerX + distance * Math.cos(Math.toRadians(360 * i / nbPlayers)));

                int y = (int) (centerY + distance * Math.sin(Math.toRadians(360 * i / nbPlayers)));

                Point point = new Point(x, y);

                this.board.setCell(point, i);

                this.playerMoves.put(player, new ArrayList<>());

                this.playerMoves.get(player).add(new Move(point));

            }

        }

        for (int i = 0; i < size; i++) {

            for (int j = 0; j < size; j++) {

                Point point = new Point(i, j);

                if (this.board.getCell(point) == -1) {

                    this.emptyPoints.add(point);

                }

            }

        }

    }

    /**
     * <b>
     * <p>
     * Returns the board of the game.
     *
     * </b>
     *
     * @return the board of the game.
     */

    public Board getBoard() {

        return this.board;

    }

    /**
     * <b>
     * <p>
     * Returns the list of players of the game.
     *
     * </b>
     *
     * @return the list of players of the game.
     */

    public List<Player> getPlayers() {

        return this.players;

    }

    /**
     * <b>
     * <p>
     * Returns the list of points that are not occupied by a symbol.
     *
     * </b>
     *
     * @return the list of points that are not occupied by a symbol.
     */

    public List<Point> getEmptyPoints() {

        return this.emptyPoints;

    }

    /**
     * <b>
     * <p>
     * Returns the list of still playing players.
     *
     * </b>
     *
     * @return the list of still playing players.
     */

    public List<Player> getStillPlayingPlayers() {

        return this.stillPlayingPlayers;

    }

    /**
     * <b>
     * <p>
     * Returns the map that associate each player to the list of his point.
     *
     * </b>
     *
     * @return the map that associate each player to the list of his point.
     */

    public Map<Player, List<Move>> getPlayerMoves() {

        return this.playerMoves;

    }

    /**
     * <b>
     * <p>
     * Returns the list of moves of the specified player.
     *
     * </b>
     *
     * @param player the player.
     * @return the list of moves of the specified player.
     */

    public List<Move> getPlayerMoves(Player player) {

        return this.playerMoves.get(player);

    }

    /**
     * <b>
     * <p>
     * Returns the head of the specified player.
     *
     * </b>
     *
     * @param player the player.
     * @return the head of the specified player.
     */

    public Point getPlayerHead(Player player) {

        return this.getLastMove(player).getHead();

    }

    /**
     * Returns the last move of the specified player.
     *
     * @param player the player.
     * @return the last move of the specified player.
     */

    public Move getLastMove(Player player) {

        List<Move> points = this.getPlayerMoves(player);

        return points.get(points.size() - 1);
    }

    /**
     * Returns the previous position of the specified player.
     *
     * @param player the player.
     * @return the previous position of the specified player.
     */
    public Point getPreviousPosition(Player player) {
        List<Move> points = this.getPlayerMoves(player);
        Move lastMove = points.get(points.size() - 1);
        if (lastMove.getLength() > 1) {
            return new Point(lastMove.getHead().getX() - lastMove.getDirection().getX(),
                    lastMove.getHead().getY() - lastMove.getDirection().getY());
        }
        return points.get(points.size() - 2).getHead();
    }


    /**
     * <b>
     * Returns the winner of the game.
     * </b>
     *
     * @return the winner of the game.
     */
    public Player getWinner() {
        if (this.stillPlayingPlayers.size() == 1) {
            return this.stillPlayingPlayers.get(0);
        }
        return null;
    }

    /**
     * <b>
     * <p>
     * Returns true if the game is over.
     *
     * </b>
     *
     * @return true if the game is over.
     */

    public boolean isOver() {

        return this.stillPlayingPlayers.size() <= 1;

    }

    /**
     * <b>
     * <p>
     * Returns true if the specified player is still playing.
     *
     * </b>
     *
     * @param player the player.
     * @return true if the specified player is still playing.
     */

    public boolean isStillPlaying(Player player) {

        return this.stillPlayingPlayers.contains(player);

    }

    /**
     * <b>
     * <p>
     * Returns a copy of the game
     *
     * </b>
     *
     * @return a copy of the game
     */

    public State copy() {

        // We copy the board

        Board boardCopy = new Board(this.board.getSize(), this.board.getGrid());

        // We copy the list of still playing players

        List<Player> stillPlayingPlayersCopy = new ArrayList<>(this.stillPlayingPlayers);

        List<Point> emptyPointsCopy = new ArrayList<>(this.emptyPoints);

        // We copy the map that associate each player to the list of his point

        Map<Player, List<Move>> playerMovesCopy = new HashMap<>();

        players.forEach(player -> playerMovesCopy.put(player, new ArrayList<>(this.playerMoves.get(player))));

        // We create the copy of the game

        return new State(boardCopy, players, stillPlayingPlayersCopy, emptyPointsCopy, playerMovesCopy);

    }

    /**
     * <b>
     * <p>
     * Returns valid moves for a specific player
     *
     * </b>
     *
     *
     *
     * <p>
     * <p>
     * A valid move is adjacent point of the last point of the specific player. And we
     * <p>
     * suppose that each player have least one point.
     *
     * </p>
     *
     * @param player the player that we want to get the valid moves
     * @return valid moves for the current player
     */

    public List<Move> getValidMoves(Player player) {

        Move lastMove = this.getLastMove(player);

        // Get all possible moves
        List<Move> possibleMoves = new ArrayList<>(lastMove.getPossibleMoves());

        // Remove all moves that are not valid
        possibleMoves.removeIf(move -> !this.board.isInBoard(move.getHead()) || !this.board.isEmpty(move.getHead()));

        return possibleMoves;
    }

    /**
     * <b>
     * <p>
     * Performs a move of each player.
     *
     * </b>
     *
     *
     *
     *
     *
     * <p>
     * <p>
     * A move is a point on the board. We ensure that each still players have a move
     *
     * </p>
     *
     * @param moves the moves of each player.
     */

    public void play(Map<Player, Move> moves) {

        List<Player> playersToRemove = new ArrayList<>();

        // We remove all player that go in the same case

        moves.forEach((player, point) -> {

            if (moves.values().stream().filter(p -> p != null && point != null && p.getHead().equals(point.getHead())).count() > 1) {

                playersToRemove.add(player);

            }

        });

        // Removing invalid players and moves

        playersToRemove.forEach(player -> {

            moves.remove(player);

            this.stillPlayingPlayers.remove(player);



        });

        // Iterating through remaining players

        moves.forEach(this::play);

        this.notifyListeners("playMoves");

    }

    /**
     * <b>
     * <p>
     * Performs a move of a specific player.
     *
     * </b>
     *
     * @param player the player that plays.
     * @param move   the move of the player.
     */

    public void play(Player player, Move move) {

        // We check if the move is valid

        if (move == null || !this.emptyPoints.contains(move.getHead())) {

            this.stillPlayingPlayers.remove(player);

        } else {

            // We add the move to the player

            if (move.getDirection().equals(this.getLastMove(player).getDirection())) {

                List<Move> moves = this.playerMoves.get(player);

                moves.remove(moves.size() - 1);

            }

            this.playerMoves.get(player).add(move);

            // We update the board

            this.board.setCell(move.getHead(), players.indexOf(player));

            // We remove the move from the list of empty points

            this.emptyPoints.remove(move.getHead());

        }
        super.notifyListeners("movePlayer", player, move);
    }


    
    @Override

    public boolean equals(Object o) {

        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        State state = (State) o;

        boolean ok = board.getSize() == state.board.getSize() &&

                Objects.equals(players, state.players) &&

                new HashSet<>(state.stillPlayingPlayers).containsAll(stillPlayingPlayers) &&

                new HashSet<>(stillPlayingPlayers).containsAll(state.stillPlayingPlayers) &&

                Objects.equals(board, state.board);

        if (ok){
            // We check if the head of each player are the same
            for (Player player : players) {
                if (!this.getLastMove(player).getHead().equals(state.getLastMove(player).getHead())) {
                    return false;
                }
            }
        }

        return ok;

    }

    @Override

    public int hashCode() {

        List<Player> playersStillPlaying = new ArrayList<>(stillPlayingPlayers);

        // We sort the players by their name
        playersStillPlaying.sort(Comparator.comparing(Player::getName));

        int hash = Objects.hash(board, players, playersStillPlaying);

        // We add the hash of each player
        for (Player player : players) {
            hash = 31 * hash + this.getLastMove(player).getHead().hashCode();
        }


        return hash;

    }


}
