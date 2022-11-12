package edu.upc.dsa.services;
import edu.upc.dsa.GameManagerImplementation;
import edu.upc.dsa.IGameManager;
import edu.upc.dsa.VideoGame;
import edu.upc.dsa.VideoGameDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api(value = "/GamePetitions")
@Path("/Minim1")
public class GameService {

    private IGameManager gameManager;

    @POST
    @ApiOperation(value = "Create a videogame", notes = "We know your game will suck anyways...")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response= VideoGameDTO.class),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/getTrack")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newTrack(VideoGameDTO received) {
        return Response.status(201).entity(received).build();
    }


}
