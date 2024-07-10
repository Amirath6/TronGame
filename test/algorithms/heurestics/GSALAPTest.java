package algorithms.heurestics;

import junit.framework.TestCase;
import model.State;
import model.board.Board;
import model.players.RandomPlayer;

import java.util.List;
import java.util.Map;

public class GSALAPTest extends TestCase {

    public void testGetValue() {

        Board board = new Board(3);
        State state = new State(board.getSize(), List.of(new RandomPlayer("RandomPlayer1"), new RandomPlayer("RandomPlayer2")));
        GSALAP gsalap = new GSALAP();
        assertEquals(Map.of(state.getPlayers().get(0), 0, state.getPlayers().get(1), 0), gsalap.getValue(state));
    }
}
