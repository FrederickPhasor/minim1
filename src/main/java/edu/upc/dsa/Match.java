package edu.upc.dsa;

public class Match {
    private final String playerId;
    private final String gameId;
    private String gameDate;
    private  int currentLevel;
    private int playerScore = 0;
    public Match(String playerId,String gameId, int startingLevel, String date){
        this.playerId = playerId;
        this.gameId = gameId;
        currentLevel = startingLevel;
        gameDate = date;
    }
    public void setScore(int amount){
        playerScore = amount;
    }
    public void addScore(int amount){
        playerScore += amount;
    }
    public int getCurrentScore(){
        return playerScore;
    }
    public int getCurrentLevel(){
        return currentLevel;
    }
    public String getGameId(){
        return gameId;
    }
    public void levelUp(){
        currentLevel++;
    }
    public void updateDate(String date){gameDate =date;}
    public String getDate(){return gameDate;}

    public String getPlayerId() {
        return playerId;
    }
}
