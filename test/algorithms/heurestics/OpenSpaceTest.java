package algorithms.heurestics;

import junit.framework.TestCase;
import model.State;
import model.board.Board;
import model.players.RandomPlayer;

import java.util.List;
import java.util.Map;

public class OpenSpaceTest extends TestCase {

    public void testGetValue() {
        Board board = new Board(3);
        State state = new State(board.getSize(), List.of(new RandomPlayer("RandomPlayer1"), new RandomPlayer("RandomPlayer2")));
        OpenSpace openSpace = new OpenSpace();
        assertEquals(Map.of(state.getPlayers().get(0), 4, state.getPlayers().get(1), 4), openSpace.getValue(state));
    }

    public void testGetValue2() {
        Board board = new Board(3, new int[][]{
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}});
        State state = new State(board.getSize(), List.of(new RandomPlayer("RandomPlayer1"), new RandomPlayer("RandomPlayer2"), new RandomPlayer("RandomPlayer3")));
        OpenSpace openSpace = new OpenSpace();
        assertEquals(Map.of(state.getPlayers().get(0), 4, state.getPlayers().get(1), 4, state.getPlayers().get(2), 4), openSpace.getValue(state));
    }
}
