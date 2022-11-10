package edu.upc.dsa;

import java.util.ArrayList;
import java.util.List;

public class Player implements  Comparable<Player>{
    private String id;
    private int points;
    private Game currentGame = null;
    ArrayList<Game> playedGames = new ArrayList<Game>();
    private int currentLevel = 0;
    public String getId(){
        return id;
    }
    public int getPoints(){
        return points;
    }
    public void setPoints(int points){
        this.points = points;
    }
    public void addPoints(int amount){
        points += amount;
    }
    public boolean isInGame(){
        return currentGame != null;
    }
    public Player(String id){
        this.id = id;
    }
    public int getCurrentLevel(){
        return currentLevel;
    }
    public Game getCurrentGame(){
        return currentGame;
    }
    public void levelUp(){
        currentLevel++;
    }
    public void setCurrentLevel(int level){
        currentLevel = level;
    }
    public void setCurrentGame(Game currentGame){
        this.currentGame = currentGame;
        currentGame.registerPlayer(this);
    }
    public void endGame(){
        currentGame = null;
    }
    public ArrayList<Game> getPlayedGames(){
        return playedGames;
    }
    @Override
    public int compareTo(Player o) {
        return Double.compare(this.points,o.points);
    }
}
