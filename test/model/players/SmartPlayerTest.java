package model.players;

import junit.framework.TestCase;

public class SmartPlayerTest extends TestCase {

    public void testGetName() {

        SmartPlayer player = new SmartPlayer("SmartPlayer", "Maxn", 1);
        assertEquals("SmartPlayer", player.getName());
    }


    public void testToString() {

        SmartPlayer player = new SmartPlayer("SmartPlayer", "Maxn", 1);
        assertEquals("SmartPlayer{name='SmartPlayer', algorithm=Maxn{nodesExplored=0, maxDepth=1}}", player.toString());
    }


}
