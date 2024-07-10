package algorithms.heurestics;

import junit.framework.TestCase;
import model.State;
import model.board.Board;
import model.players.RandomPlayer;

import java.util.List;
import java.util.Map;

public class VoronoiTest extends TestCase {

    public void testGetValue() {
        Board board = new Board(3);
        State state = new State(board.getSize(), List.of(new RandomPlayer("RandomPlayer1"), new RandomPlayer("RandomPlayer2")));
        Voronoi voronoi = new Voronoi();
        assertEquals(Map.of(state.getPlayers().get(0), 2, state.getPlayers().get(1), 2), voronoi.getValue(state));
    }

}
