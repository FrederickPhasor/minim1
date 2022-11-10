package edu.upc.dsa;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
public class GameManagerTest {
    GameManagerImplementation gm;
    @Before
    public void setUp(){
        gm = new GameManagerImplementation();
        gm.createPlayer("Anna");
        gm.createPlayer("Pedro");
        gm.createGame("0", "Hell Room",666);
    }
    @After
    public void tearDown() {
        gm = null;
    }
    @Test
    public void testCreateGame() {
        gm.createGame("1", "Beasts Realm",10);
        gm.createGame("2", "Celestial Realm",777);
        assertEquals(gm.getAmountOfGames(),3);
    }
    @Test
    public void testStartGame() {
        gm.startGame("0", "Anna");
        assertEquals(gm.isPlayerInGame("Anna","0"),true);
    }
    @Test
    public void testLevelUp() {
        testStartGame();
        gm.levelUpPlayer("Anna",100,"28/10/1999");
        // El inicial es el 0
        assertEquals(gm.getPlayerLevel("Anna"), 1);
    }
    @Test
    public void testEndGame() {
        testLevelUp();
        gm.endGameOfPlayer("Anna");
        assertEquals(gm.isPlayerInAnyGame("Anna"), false);
    }
}
