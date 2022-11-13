package edu.upc.dsa.services;
import edu.upc.dsa.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/GamePetitions")
@Path("/Minim1")
public class GameService {

    private IGameManager gameManager;

    public GameService() {
        this.gameManager = GameManagerImplementation.getInstance();
        gameManager.createGame("0", "A game about killing plants", 4);
        gameManager.createGame("1", "A game where you are a plant running away from plant killers", 4);
    }

    @POST
    @ApiOperation(value = "Create a videogame", notes = "The Funnier the game the better!")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = VideoGameDTO.class),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/Create VideoGame")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createVideoGame(VideoGameDTO received) {
        gameManager.createGame(received.id, received.description, received.levelsAmount);
        return Response.status(201).entity(received).build();
    }

    @POST
    @ApiOperation(value = "Create a Match", notes = "Play your favorite game!")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Match.class),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/Start a Match")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newTrack(Match createdMatch) {
        return Response.status(201).entity(createdMatch).build();
    }

    @POST
    @ApiOperation(value = "Create a player")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Player.class),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/Create Player")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createVideoGame(Player received) {
        gameManager.createPlayer(received.getId(), received.getName());
        return Response.status(201).entity(received).build();
    }

    @Path("/Get Current Score/{playerId}")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getCurrentScoreFromPlayer(@PathParam(("playerId")) String id) {

        return "" + gameManager.getPlayerScore(id);
    }

    @Path("/Get Current Level/{playerId}")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getCurrentLevelFromPlayer(@PathParam(("playerId")) String id) {

        return "" + gameManager.getPlayerLevel(id);
    }

    @DELETE
    @ApiOperation(value = "End Game", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Track not found")
    })
    @Path("/endGameTo/{id}")
    public Response endGameToPlayer(@PathParam("id") String id) {
        gameManager.endMatchOfPlayer(id);
        return Response.status(201).build();
    }

    @GET
    @ApiOperation(value = "Get Involved Players In Game Sorted By Score", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", responseContainer = "List"),
            @ApiResponse(code = 404, message = "Track not found")
    })
    @Path("/involvedPlayers/{gameId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInvolvedPlayersSortedByScore(@PathParam("gameId") String gameId) {
        List<String> results = gameManager.getInvolvedPlayersInGameSortedByScore(gameId);
        GenericEntity<List<String>> result = new GenericEntity<List<String>>(results) {
        };
        return Response.status(201).entity(result).build();

    }
}
