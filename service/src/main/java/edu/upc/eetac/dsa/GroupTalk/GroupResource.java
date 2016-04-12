package edu.upc.eetac.dsa.GroupTalk;

import edu.upc.eetac.dsa.GroupTalk.dao.AuthTokenDAOImpl;
import edu.upc.eetac.dsa.GroupTalk.dao.GroupDAO;
import edu.upc.eetac.dsa.GroupTalk.dao.GroupDAOImpl;
import edu.upc.eetac.dsa.GroupTalk.dao.UserAlreadyExistsException;
import edu.upc.eetac.dsa.GroupTalk.entity.AuthToken;
import edu.upc.eetac.dsa.GroupTalk.entity.Group;
import edu.upc.eetac.dsa.GroupTalk.entity.GroupCollection;

import javax.ws.rs.*;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by Rosa on 27/03/16.
 */
@Path("groups")
public class GroupResource {
    @Context
    private SecurityContext securityContext;
    @POST

    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(GroupTalkMediaType.GROUPTALK_GROUP)
    public Response registerGroup(@FormParam("nombre") String nombre, @Context UriInfo uriInfo) throws URISyntaxException {
        if(nombre==null)
            throw new BadRequestException("all parameters are mandatory");

        if(securityContext.isUserInRole("admin")==false)
            throw new ForbiddenException("No se puede admin");

        GroupDAO groupDAO = new GroupDAOImpl();
        Group group = null;
        try{
            group = groupDAO.createGroup(nombre);
        }catch(SQLException e){
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + group.getId());
        return Response.created(uri).type(GroupTalkMediaType.GROUPTALK_GROUP).entity(group).build();
    }


    @Path("/{groupid}")
    @POST
    @Produces(GroupTalkMediaType.GROUPTALK_GROUP)
    public void joinGroup (@PathParam("groupid") String groupid, @Context UriInfo uriInfo){

        GroupDAO groupDAO = new GroupDAOImpl();
        AuthToken authToken= null;
    try {

        groupDAO.unirseGrupo(securityContext.getUserPrincipal().getName(),groupid);

    } catch (SQLException e) {
        throw new InternalServerErrorException();
    }

}
    @Path("/leave/{groupid}")
    @DELETE
    public void leavegroup(@PathParam("groupid") String groupid){
        GroupDAO groupDAO = new GroupDAOImpl();
        try
        {
         groupDAO.dejarGrupo(securityContext.getUserPrincipal().getName(),groupid);

        }
        catch (SQLException e){
            throw new InternalServerErrorException();
        }
    }

    @GET
    @Produces(GroupTalkMediaType.GROUPTALK_GROUP)
    public GroupCollection getGroups(){
        GroupCollection groupCollection = null;
        GroupDAO groupDAO = new GroupDAOImpl();
        try {

           groupCollection = groupDAO.getGroups();
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return groupCollection;
    }

}
