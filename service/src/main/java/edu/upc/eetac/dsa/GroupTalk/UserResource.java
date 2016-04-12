package edu.upc.eetac.dsa.GroupTalk;

import edu.upc.eetac.dsa.GroupTalk.dao.AuthTokenDAOImpl;
import edu.upc.eetac.dsa.GroupTalk.dao.UserAlreadyExistsException;
import edu.upc.eetac.dsa.GroupTalk.dao.UserDAO;
import edu.upc.eetac.dsa.GroupTalk.dao.UserDAOImpl;
import edu.upc.eetac.dsa.GroupTalk.entity.AuthToken;
import edu.upc.eetac.dsa.GroupTalk.entity.User;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by Rosa on 27/03/16.
 */
@Path("users")
public class UserResource {
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(GroupTalkMediaType.GROUPTALK_AUTH_TOKEN)
    public Response registerUser(@FormParam("loginid") String loginid, @FormParam("password") String password, @Context UriInfo uriInfo) throws URISyntaxException {
        if(loginid == null || password == null)
            throw new BadRequestException("all parameters are mandatory");
        UserDAO userDAO = new UserDAOImpl();
        User user = null;
        AuthToken authenticationToken = null;
        try{
            user = userDAO.createUser(loginid, password);
            authenticationToken = (new AuthTokenDAOImpl()).createAuthToken(user.getId());
        }catch (UserAlreadyExistsException e){
            throw new WebApplicationException("loginid already exists", Response.Status.CONFLICT);
        }catch(SQLException e){
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + user.getId());
        return Response.created(uri).type(GroupTalkMediaType.GROUPTALK_AUTH_TOKEN).entity(authenticationToken).build();
    }
}
