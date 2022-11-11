package edu.upc.dsa;

import java.util.Hashtable;

public class VideoGame {
    private String id;
    private String description;
    private int levelsAmount;
    public String getID(){
        return id;
    }
    public String getDescription()
    {
        return description;
    }
    public int getLevelsAmount()
    {
        return levelsAmount;
    }
    final Hashtable<String, MatchRecord> records = new Hashtable<>();
    public VideoGame(String id, String description, int levelsAmount)
    {
        this.id = id;
        this.description = description;
        this.levelsAmount = levelsAmount;
    }
    public void RegisterRecord(String playerId, int newReachedScore,String gameId, String date){
        MatchRecord previousRecord;
        if(records.contains(playerId)){
             previousRecord = records.get(playerId);
            if(previousRecord.getReachedScore() < newReachedScore){
                previousRecord.setScore(newReachedScore);
            }
            return;
        }
        previousRecord = new MatchRecord(playerId, newReachedScore,gameId,date);
        records.put(playerId,previousRecord);
    }
}