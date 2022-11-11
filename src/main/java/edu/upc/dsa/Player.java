package edu.upc.dsa;
import java.util.ArrayList;
import java.util.Objects;
public class Player {
    public String getId(){ return id; }
    public String getName(){return name;}
    public boolean isInMatch(){
        return currentMatch != null;
    }
    public Player(String id, String name){
        this.id = id;
        this.name = name;
    }
    private final String id;
    private final String name;
    private Match currentMatch = null;
    private final ArrayList<MatchRecord> matchesRegister = new ArrayList<MatchRecord>();

    public Match getCurrentMatch(){
        return currentMatch;
    }
    public void setCurrentMatch(Match currentGame){
        this.currentMatch = currentGame;
    }
    public void endMatch(){
        MatchRecord newRecord = new MatchRecord(id,currentMatch.getCurrentScore(),currentMatch.getGameId(),currentMatch.getDate());
        matchesRegister.add(newRecord);
        currentMatch = null;
    }
    public ArrayList<MatchRecord> getMatchesRegisterFromGame(String gameId){
        ArrayList<MatchRecord> result = new ArrayList<MatchRecord>();
        if(!hasPlayed(gameId))
            return null;
        for (MatchRecord r:matchesRegister) {
            if(Objects.equals(r.getGameId(), gameId))
                result.add(r);
        }
        return result;
    }

    private boolean hasPlayed(String gameId){
        // Check if we have register of this game in our matches.
        for (MatchRecord r:matchesRegister) {
            if(r.getGameId().equals(gameId)) {
                return true;
            }
        }
        return false;
    }
}
