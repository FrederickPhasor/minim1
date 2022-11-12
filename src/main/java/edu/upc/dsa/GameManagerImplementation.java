package edu.upc.dsa;

import org.apache.log4j.Logger;

import java.util.*;

public class GameManagerImplementation implements  IGameManager {
    private static IGameManager Instance;
    final static Logger logger = Logger.getLogger(GameManagerImplementation.class);
    public static IGameManager getInstance() {
        if (Instance==null)
            Instance = new GameManagerImplementation();
        return Instance;
    }
    Hashtable<String, VideoGame> createdVideoGames = new Hashtable<String, VideoGame>();
    Hashtable<String, Player> players = new Hashtable<String,Player>();

    @Override
    public void createPlayer(String id, String name) {
    logger.info("Creating player, current players count : " + players.size());
        Player newPlayer = new Player(id,name);
        if(doesPlayerExists(id)){
            logger.error("This Player Already Exists!");
            return;
        }
        players.put(id,newPlayer);
        logger.info("Player was created, number of players now :  " + players.size());
    }
    @Override
    public void createGame(String id, String description, int levelsAmount) {
        logger.info("Creating game, current games count : " + createdVideoGames.size());
        if(createdVideoGames.contains(id))
        {
            logger.warn("There is already a game with this same id : " + id);
            return;
        }
        VideoGame newVideoGame = new VideoGame(id,description,levelsAmount);
        createdVideoGames.put(id, newVideoGame);
        logger.info("Game Created, current games count : " + createdVideoGames.size());
    }

    @Override
    public void startMatch(String gameId, String playerId, String date) {
        if(!doesVideoGameExist(gameId)){
            logger.error("This Player Does Not Exist");
            return;
        }
        if(!doesPlayerExists(playerId)){
            logger.error("The Game Requested Does Not Exist.");
            return;
        }
        Player player = players.get(playerId);
        if(player.isInMatch()){
            logger.error("Player with id : "+ playerId+ "is already in a game. Finish the current game first.");
            return;
        }
        Match newMatch = new Match(playerId,gameId ,1,date);
        logger.info("Starting game on player with id : " + playerId + " isThisPlayerInGame? : " + player.isInMatch());
        newMatch.setScore(50);
        player.setCurrentMatch(newMatch);
        logger.info("Game started on player with id : " + playerId + " isThisPlayerInGame? : " + player.isInMatch());

    }
    private boolean doesPlayerExists(String id){
        return players.containsKey(id);
    }
    private boolean doesVideoGameExist(String id){
         return createdVideoGames.containsKey(id);
    }

    @Override
    public int getPlayerLevel(String playerID) {
        if(!doesPlayerExists(playerID)){
            logger.error("This Player Does Not Exist");
            return -1;
        }
        Player requestedPlayer  = players.get(playerID);
        if(!requestedPlayer.isInMatch()){
            logger.error("Player is not currently in a match, can not check current level of non-existing match");
            return -1;
        }
        Match currentMatch = requestedPlayer.getCurrentMatch();
        int currentLevel =  currentMatch.getCurrentLevel();
        int totalLevels = createdVideoGames.get(currentMatch.getGameId()).getLevelsAmount();
        logger.info("Player with id : " + requestedPlayer.getId()
                + " is playing a game with id : "
                + currentMatch.getGameId()
                + " and its at level : "
                + currentLevel + "/"+totalLevels);
        return currentLevel;
    }

    @Override
    public int getPlayerScore(String playerID) {
        if(!doesPlayerExists(playerID)){
            logger.error("This Player Does Not Exist");
            return -1;
        }
        Player requestedPlayer  = players.get(playerID);
        if(!requestedPlayer.isInMatch()){
            logger.error("Player is not currently in a game, can not check current score");
            return -1;
        }
        Match playerCurrentMatch = requestedPlayer.getCurrentMatch();
        int currentScore = playerCurrentMatch.getCurrentScore();
        logger.info("Player with id : " + playerID
                + " is playing a game with id : "
                + playerCurrentMatch.getGameId() +
                " and with a score of : " + currentScore);
        return currentScore;
    }

    @Override
    public void levelUpPlayer(String playerID, int rewardPoints, String ddmmyyyy) {

        if(!doesPlayerExists(playerID)){
            logger.error("Player Does Not Exist");
            return;
        }
        Player requestedPlayer  = players.get(playerID);
        if(!requestedPlayer.isInMatch()){
            logger.error("Player with id: " + playerID + "is not currently in a match");
            return;
        }
        Match currentMatch = requestedPlayer.getCurrentMatch();
        logger.info("Leveling up player, , current level/points : "+currentMatch.getCurrentLevel() + "/"+ currentMatch.getCurrentScore());
        currentMatch.addScore(rewardPoints);
        currentMatch.levelUp();
        currentMatch.updateDate(ddmmyyyy);
        int totalLevels = createdVideoGames.get(currentMatch.getGameId()).getLevelsAmount();
        if(currentMatch.getCurrentLevel() >= totalLevels){
            currentMatch.addScore(100);
            endMatchOfPlayer(playerID);
            logger.info("Player with id : " + playerID
                    +" has finished game with id :"
                    + currentMatch.getGameId()
                    + " with a score of : "
                    + currentMatch.getCurrentScore());
        }
        logger.info("Updated level : "+ currentMatch.getCurrentLevel() + "/"+ totalLevels);
    }

    @Override
    public void endMatchOfPlayer(String playerID) {
        if(!doesPlayerExists(playerID)){
            logger.error("Player Does Not Exist");
            return;
        }
        Player player  = players.get(playerID);
        if(!player.isInMatch()){
            logger.error("Player is not currently in a game");
            return;
        }
        Match currentMatch = player.getCurrentMatch();
        VideoGame videoGamePlayed = createdVideoGames.get(currentMatch.getGameId());
        videoGamePlayed.RegisterRecord(playerID, currentMatch.getCurrentScore(), currentMatch.getGameId(), currentMatch.getDate());
        player.endMatch();
        logger.info("Player with id : "
                + playerID
                + " finished match of game with id : " + videoGamePlayed.getID()+ " with : "
                + currentMatch.getCurrentScore()
                +" points");
    }
    @Override
    public ArrayList<String> getInvolvedPlayersInGameSortedByScore(String gameId) {
        ArrayList<String> sortedByScorePlayersOfGameNames = new ArrayList<>();
        if(doesVideoGameExist(gameId)){
            logger.error("Game Does Not Exist");
            return null;
        }
        VideoGame requestedVideoGame = createdVideoGames.get(gameId);
        ArrayList<MatchRecord> nonSortedList =new ArrayList<MatchRecord>(requestedVideoGame.records.values());
        Collections.sort(nonSortedList);
        for (MatchRecord r: nonSortedList) {
            String playerName = players.get(r.getPlayerId()).getName();
            sortedByScorePlayersOfGameNames.add(playerName);
        }
        return sortedByScorePlayersOfGameNames;
    }

    @Override
    public ArrayList<MatchRecord> getMatchesDoneByPlayerInGame(String playerId,String gameId) {
        if(!doesPlayerExists(playerId)){
            logger.warn("Player Does Not Exist");
            return null;
        }
        if(!doesVideoGameExist(gameId)){
            logger.error("Game Does Not Exist");
            return null;
        }
        Player requestedPLayer = players.get(playerId);
        ArrayList<MatchRecord> result =requestedPLayer.getMatchesRegisterFromGame(gameId);
        if(result == null){
            logger.warn("This player has not played any matches of : " + gameId);
        }
        return result;
    }
    public int getAmountOfGames(){
        return createdVideoGames.size();
    }
    public boolean isPlayerInMatchOfGame(String playerId, String gameId){
        if(!doesVideoGameExist(gameId)){
            logger.error("Game with id : " + gameId + "does not exist.");
            return false;
        }
        if(!doesPlayerExists(playerId)){
            logger.error("Player with id : " + playerId + "does not exist.");
            return false;
        }
        return Objects.equals(players.get(playerId).getCurrentMatch().getGameId(), createdVideoGames.get(gameId).getID());
    }
    public boolean isPlayerInAnyMatch(String playerId){
        if(!doesPlayerExists(playerId)){
            logger.error("Player with id : " + playerId + "does not exist.");
            return false;
        }
        return players.get(playerId).isInMatch();
    }
    public VideoGame getGame(String id)
    {
        return createdVideoGames.get(id);
    }

}
