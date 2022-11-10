package edu.upc.dsa;

import java.util.List;

public interface IGameManager {
    public void createPlayer(String id);
    public void createGame(String id, String description, int levelsAmount);
    public void startGame(String gameID, String playerID);
    public int getPlayerLevel(String playerID);
    public int getPlayerScore(String playerID);
    public void levelUpPlayer(String playerID, int rewardPoints, String ddmmyyyy);
    public void endGameOfPlayer(String playerID);
    public List<Player> getInvolvedPlayersInGameSortedByScore(String gameID);
    public List<Game> getGamesPlayedByPlayer(String playerID);
    // public List<InGameDataDTO> getPlayerActivity(String PlayerID);
}
