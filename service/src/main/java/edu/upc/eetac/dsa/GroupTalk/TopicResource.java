package edu.upc.eetac.dsa.GroupTalk;

import edu.upc.eetac.dsa.GroupTalk.dao.TopicDAO;
import edu.upc.eetac.dsa.GroupTalk.dao.TopicDAOImpl;
import edu.upc.eetac.dsa.GroupTalk.entity.AuthToken;
import edu.upc.eetac.dsa.GroupTalk.entity.RelacionUserGrupo;
import javax.ws.rs.core.Response;
import edu.upc.eetac.dsa.GroupTalk.entity.Topic;
import edu.upc.eetac.dsa.GroupTalk.entity.TopicCollection;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
/**
 * Created by Rosa on 28/03/16.
 */
@Path("{groupid}")
public class TopicResource {

    @Context
    private SecurityContext securityContext;

    @POST

    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(GroupTalkMediaType.GROUPTALK_TOPIC)
    public Response createTema(@PathParam("groupid") String groupid, @FormParam("titulo") String titulo, @FormParam("content") String content, @Context UriInfo uriInfo) throws URISyntaxException {
        if (titulo == null || content == null)
            throw new BadRequestException("all parameters are mandatory");
        String userid = securityContext.getUserPrincipal().getName();
        TopicDAO topicDAO = new TopicDAOImpl();
        AuthToken authenticationToken = null;
        Topic topic = null;

        try {
            RelacionUserGrupo relacionUserGrupo = topicDAO.getRelacion(userid, groupid);
            if (relacionUserGrupo == null)
                throw new ForbiddenException("You must suscribe to group to create temas");
            topic = topicDAO.createTopic(userid,titulo,content, groupid);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + topic.getId());
        return Response.created(uri).type(GroupTalkMediaType.GROUPTALK_TOPIC).entity(topic).build();
    }

    @GET
    @Produces(GroupTalkMediaType.GROUPTALK_TOPIC_COLLECTION)
    public TopicCollection getTema(@PathParam("groupid") String groupid){
        TopicCollection topicCollection = null;
        TopicDAO topicDAO = new TopicDAOImpl();
        String userid = securityContext.getUserPrincipal().getName();
        try {
            RelacionUserGrupo relacionUserGrupo = topicDAO.getRelacion(userid,groupid);
            if (relacionUserGrupo == null)
                throw new ForbiddenException("You must suscribe to group to see temas");
            topicCollection = topicDAO.getTopics(groupid);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }

        return topicCollection;
    }

    @Path("/tema/{idtema}")
    @PUT
    @Consumes(GroupTalkMediaType.GROUPTALK_TOPIC)
    @Produces(GroupTalkMediaType.GROUPTALK_TOPIC)
    public Topic updateTema(@PathParam("idtema") String idtema, Topic topic) {
        if(topic == null)
            throw new BadRequestException("entity is null");

        if(!idtema.equals(topic.getId()))
            throw new BadRequestException("path parameter id and entity parameter id doesn't match");

        String userid = securityContext.getUserPrincipal().getName();
        if(!userid.equals(topic.getAutor_tema()))
            throw new ForbiddenException("operation not allowed");

        TopicDAO topicDAO = new TopicDAOImpl();
        try {
            topic = topicDAO.updateTopic(idtema,topic.getTitulo(),topic.getContent());
            if(topic == null)
                throw new NotFoundException("Tema with id = "+idtema+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return topic;
    }

    @Path("/tema/{idtema}")
    @DELETE
    public void deleteTopic(@PathParam("idtema") String idtema) {
        String userid = securityContext.getUserPrincipal().getName();
        TopicDAO topicDAO = new TopicDAOImpl();
        try {
            String ownerid = topicDAO.getTopicById(idtema).getAutor_tema();
            if(!userid.equals(ownerid))
                throw new ForbiddenException("operation not allowed");
            if(!topicDAO.deleteTopic(idtema))
                throw new NotFoundException("Sting with id = "+idtema+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

}



