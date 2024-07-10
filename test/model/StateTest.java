package model;

import junit.framework.TestCase;
import model.players.RandomPlayer;

import java.util.List;

public class StateTest extends TestCase {

    public void testGetBoard() {
        State state = new State(2, List.of(new RandomPlayer("RandomPlayer1"), new RandomPlayer("RandomPlayer2")));
        assertEquals(2, state.getBoard().getSize());
    }

    public void testGetPlayers() {
        State state = new State(2, List.of(new RandomPlayer("RandomPlayer1"), new RandomPlayer("RandomPlayer2")));
        assertEquals(2, state.getPlayers().size());
    }

    public void testGetWinner() {
        State state = new State(2, List.of(new RandomPlayer("RandomPlayer1"), new RandomPlayer("RandomPlayer2")));
        assertEquals(null, state.getWinner());
    }

}
