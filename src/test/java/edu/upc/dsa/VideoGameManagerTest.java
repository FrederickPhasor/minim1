package edu.upc.dsa;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class VideoGameManagerTest {
    GameManagerImplementation gm;
    @Before
    public void setUp(){
        gm = new GameManagerImplementation();
        gm.createPlayer("0","Anna");
        gm.createPlayer("1","Pedro");
        gm.createGame("0", "A game about hunting demons",666);
        gm.startMatch("0","1","11/05/1115");
    }
    @After
    public void tearDown() {
        gm = null;
    }
    @Test
    public void testCreatePlayers() {
        gm.createPlayer("2","Toni");
        gm.createPlayer("3","Juan");
        assertEquals(gm.players.size(),4);
    }
    @Test
    public void testCreateGame() {
        gm.createGame("1", "A game about fighting as a god against infinite hordes of beasts",10);
        gm.createGame("2", "A game about fighting a famous spanish youtuber",777);
        assertEquals(gm.getAmountOfGames(),3);
    }
    @Test
    public void testStartMatch() {
        gm.startMatch("0","0","28/10/1999");
        assertEquals(true,gm.isPlayerInMatchOfGame("0", "0"));
    }
    @Test
    public void testLevelUp() {
        gm.levelUpPlayer("1",100,"28/10/1999");
        assertEquals( 2,gm.getPlayerLevel("1"));
        assertEquals(150,gm.getPlayerScore("1"));
    }
    @Test
    public void testEndMatch() {
        gm.startMatch("0","0","28/09/2003");
        assertTrue(gm.isPlayerInAnyMatch("0"));
        gm.endMatchOfPlayer("0");
        assertFalse(gm.isPlayerInAnyMatch("0"));
    }
    @Test
    public void testGetPlayersMatchesOfGameRegister(){
        testLevelUp();
        gm.endMatchOfPlayer("1");
        ArrayList<MatchRecord> records =  gm.getMatchesDoneByPlayerInGame("1","0");
        assertEquals(1,records.size());
        String gameId = records.get(0).getGameId();
        int playerScore = records.get(0).getReachedScore();
        String date = records.get(0).getDate();
        assertEquals("0",gameId);
        assertEquals(150,playerScore);
        assertEquals("28/10/1999",date);
    }
}
