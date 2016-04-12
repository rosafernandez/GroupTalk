package edu.upc.eetac.dsa.GroupTalk;

import edu.upc.eetac.dsa.GroupTalk.dao.*;
import edu.upc.eetac.dsa.GroupTalk.entity.*;
import edu.upc.eetac.dsa.GroupTalk.entity.Response;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by Rosa on 28/03/16.
 */

@Path("{idgrupo}/topicResponse/{idtema}")
public class ResponseResource {
    @Context
    private SecurityContext securityContext;
    @POST

    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(GroupTalkMediaType.GROUPTALK_RESPONSE)
    public javax.ws.rs.core.Response createResponse(@PathParam("idgrupo") String idgrupo,@PathParam("idtema") String idtema,@FormParam("content") String content, @Context UriInfo uriInfo) throws URISyntaxException {
        if(content==null)
            throw new BadRequestException("all parameters are mandatory");

        ResponseDAO responseDAO = new ResponseDAOImpl();
        Response response =null;
        String userid = securityContext.getUserPrincipal().getName();
        TopicDAO topicDAO = new TopicDAOImpl();

        try {
            RelacionUserGrupo relacionUserGrupo = topicDAO.getRelacion(userid,idgrupo);
            if (relacionUserGrupo == null)
                throw new ForbiddenException("No perteneces a este grupo");
            response=responseDAO.createResponse(idtema,content,userid);
            if(response == null)
                throw new NotFoundException("Tema with id = "+idtema+" doesn't exist");


        }catch(SQLException e){
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + response.getId());
        return javax.ws.rs.core.Response.created(uri).type(GroupTalkMediaType.GROUPTALK_RESPONSE).entity(response).build();
    }

    @Path("/respuesta/{idrespuesta}")
    @DELETE
    public void deleteRespuesta(@PathParam("idgrupo") String idgrupo,@PathParam("idtema") String idtema, @PathParam("idrespuesta") String idrespuesta) {
        String userid = securityContext.getUserPrincipal().getName();
        ResponseDAO responseDAO = new ResponseDAOImpl();
        TopicDAO topicDAO = new TopicDAOImpl();
        try {
            String ownerid = responseDAO.getRespuestaById(idrespuesta).getAutor_respuesta();
            String owneridtema = topicDAO.getTopicById(idtema).getAutor_tema();
            if(!userid.equals(ownerid)&&!securityContext.isUserInRole("admin")&&!userid.equals(owneridtema))
                throw new ForbiddenException("operation not allowed");
            if(!responseDAO.deleteResponse(idrespuesta))
                throw new NotFoundException("Respuesta with id = "+idrespuesta+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

    @GET
    @Produces(GroupTalkMediaType.GROUPTALK_RESPONSE_COLLECTION)
    public ResponseCollection getResponses(@PathParam("idgrupo") String idgrupo,@PathParam("idtema") String idtema){
        ResponseCollection responseCollection = null;
        ResponseDAO responseDAO = new ResponseDAOImpl();
        String userid = securityContext.getUserPrincipal().getName();
        try {
            responseCollection = responseDAO.getResponses(idtema);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }

        return responseCollection;
    }
}
