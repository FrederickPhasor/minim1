package edu.upc.dsa;

public class MatchRecord implements  Comparable<MatchRecord>{
    private final String playerId;
    private int scoreReached;
    private String gameId;
    private String gameDate;
    public MatchRecord(String playerId, int scoreReached,String gameId, String gameDate){
        this.playerId = playerId;
        this.gameId = gameId;
        this.scoreReached = scoreReached;
        this.gameDate = gameDate;
    }
    public String getPlayerId(){
        return playerId;
    }
    public int getReachedScore(){
        return scoreReached;
    }
    public void setScore(int amount){
        scoreReached = amount;
    }
    public String getGameId(){
        return gameId;
    }
    public String getDate(){return gameDate;}
    @Override
    public int compareTo(MatchRecord o) {
        return Double.compare(scoreReached,o.getReachedScore());
    }
}
