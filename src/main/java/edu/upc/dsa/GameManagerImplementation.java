package edu.upc.dsa;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

public class GameManagerImplementation implements  IGameManager {
    private static IGameManager Instance;
    final static Logger logger = Logger.getLogger(GameManagerImplementation.class);
    public static IGameManager getInstance() {
        if (Instance==null)
            Instance = new GameManagerImplementation();
        return Instance;
    }
    Hashtable<String, Game> availableGames = new Hashtable<String,Game>();
    Hashtable<String, Player> players = new Hashtable<String,Player>();

    @Override
    public void createPlayer(String id) {
    logger.info("Creating player, current players count : " + players.size());
        Player newPlayer = new Player(id);
        if(doesPlayerExists(id)){
            logger.error("This Player Already Exists!");
            return;
        }
        players.put(id,newPlayer);
        logger.info("Player was created, number of players now :  " + players.size());

    }

    @Override
    public void createGame(String id, String description, int levelsAmount) {
        logger.info("Creating game, current games count : " + availableGames.size());
        if(availableGames.contains(id))
        {
            logger.error("There is already a game with this same id : " + id);
            return;
        }
        Game newGame = new Game(id,description,levelsAmount);
        availableGames.put(id,newGame);
        logger.info("Game Created, current games count : " + availableGames.size());
    }

    @Override
    public void startGame(String gameID, String playerID) {
        if(!doesGameExists(gameID)){
            logger.error("The Game Requested Does Not Exist.");
            return;
        }
        if(!doesPlayerExists(playerID)){
            logger.error("This Player Does Not Exist");
            return;
        }
        Game requestedGame = availableGames.get(gameID);
        Player player = players.get(playerID);

        if(player.isInGame()){
            logger.error("Player is already in a game. Finish the current game first.");
            return;
        }
        logger.info("Starting game on player with id : " + playerID + " isThisPlayerInGame? : " + player.isInGame());
        player.setCurrentGame(requestedGame);
        player.setCurrentLevel(0);
        player.setPoints(50);
        logger.info("Game started on player with id : " + playerID + " isThisPlayerInGame? : " + player.isInGame());

    }
    private boolean doesPlayerExists(String id){
        return players.get(id) != null;
    }
    private boolean doesGameExists(String id){
         return availableGames.get(id) != null;
    }

    @Override
    public int getPlayerLevel(String playerID) {
        if(!doesPlayerExists(playerID)){
            logger.error("This Player Does Not Exist");
            return -1;
        }
        Player requestedPlayer  = players.get(playerID);
        if(!requestedPlayer.isInGame()){
            logger.error("Player is not currently in a game, can not check current level");
            return -1;
        }
        return requestedPlayer.getCurrentLevel();

    }

    @Override
    public int getPlayerScore(String playerID) {
        if(!doesPlayerExists(playerID)){
            logger.error("This Player Does Not Exist");
            return -1;
        }
        Player requestedPlayer  = players.get(playerID);
        if(!requestedPlayer.isInGame()){
            logger.error("Player is not currently in a game, can not check current score");
            return -1;
        }
        return requestedPlayer.getPoints();
    }

    @Override
    public void levelUpPlayer(String playerID, int rewardPoints, String ddmmyyyy) {

        if(!doesPlayerExists(playerID)){
            logger.error("Player Does Not Exist");
            return;
        }
        Player requestedPlayer  = players.get(playerID);
        if(!requestedPlayer.isInGame()){
            logger.error("Player is not currently in a game");
            return;
        }
        Game requestedGame = requestedPlayer.getCurrentGame();
        logger.info("Leveling up player, , current level/points : "+requestedPlayer.getCurrentLevel() + "/"+ requestedPlayer.getPoints());
        requestedPlayer.addPoints(rewardPoints);
        requestedPlayer.levelUp();
        if(requestedPlayer.getCurrentLevel() >= requestedPlayer.getCurrentGame().getLevelsAmount()){
            requestedPlayer.addPoints(100);
            endGameOfPlayer(playerID);
        }
        logger.info("Leveling up player, current level/points : "+requestedPlayer.getCurrentLevel() + "/"+ requestedPlayer.getPoints());
    }

    @Override
    public void endGameOfPlayer(String playerID) {
        if(!doesPlayerExists(playerID)){
            logger.error("Player Does Not Exist");
            return;
        }
        Player requestedPlayer  = players.get(playerID);
        if(!requestedPlayer.isInGame()){
            logger.error("Player is not currently in a game");
            return;
        }
        requestedPlayer.endGame();
    }

    @Override
    public List<Player> getInvolvedPlayersInGameSortedByScore(String gameID) {
        if(!doesGameExists(gameID)){
            logger.error("Game Does Not Exist");
            return null;
        }
        List<Player> nonSortedList =new ArrayList<Player>(players.values());
        Collections.sort(nonSortedList);
        return nonSortedList;
    }

    @Override
    public List<Game> getGamesPlayedByPlayer(String playerID) {
        if(!doesPlayerExists(playerID)){
            logger.error("Player Does Not Exist");
            return null;
        }
        Player requestedPLayer = players.get(playerID);
        return requestedPLayer.getPlayedGames();

    }
    public int getAmountOfGames(){
        return availableGames.size();
    }
    public boolean isPlayerInGame(String playerID, String gameID){
        if(!doesGameExists(gameID)){
            return false;
        }
        if(!doesPlayerExists(playerID)){
            return false;
        }
        return players.get(playerID).getCurrentGame().getID() == availableGames.get(gameID).getID();
    }
    public boolean isPlayerInAnyGame(String playerID){
        if(!doesPlayerExists(playerID)){
            return false;
        }
        return players.get(playerID).isInGame();
    }
}
