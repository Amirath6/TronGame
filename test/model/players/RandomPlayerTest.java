package model.players;

import junit.framework.TestCase;

public class RandomPlayerTest extends TestCase {

    public void testGetName() {
        RandomPlayer player = new RandomPlayer("RandomPlayer");
        assertEquals("RandomPlayer", player.getName());
    }

    public void testToString() {
        RandomPlayer player = new RandomPlayer("RandomPlayer");
        assertEquals("RandomPlayer", player.toString());
    }
}
