package edu.upc.dsa;

import java.util.ArrayList;

public class Game {
    private String id;
    private String description;
    private int levelsAmount;
    ArrayList<Player> playersInGame = new ArrayList<Player>();
    public String getDescription()
    {
        return description;
    }
    public String getID(){
        return id;
    }
    public int getLevelsAmount()
    {
        return levelsAmount;
    }
    public void registerPlayer(Player newPlayer){
        if(playersInGame.contains(newPlayer))
            return;
        playersInGame.add(newPlayer);
    }
    public Game(String id, String description, int levelsAmount)
    {
        this.id = id;
        this.description = description;
        this.levelsAmount = levelsAmount;
    }



}
