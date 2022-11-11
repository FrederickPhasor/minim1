package edu.upc.dsa;
import java.util.ArrayList;

public interface IGameManager {
    public void createPlayer(String id, String playerName);
    public void createGame(String id, String description, int levelsAmount);
    public void startMatch(String gameId, String playerId, String date);
    public int getPlayerLevel(String playerID);
    public int getPlayerScore(String playerID);
    public void levelUpPlayer(String playerID, int rewardPoints, String ddmmyyyy);
    public void endMatchOfPlayer(String playerID);
    public ArrayList<String> getInvolvedPlayersInGameSortedByScore(String gameId);
    public ArrayList<MatchRecord> getMatchesDoneByPlayerInGame(String playerID,String gameId);
}
