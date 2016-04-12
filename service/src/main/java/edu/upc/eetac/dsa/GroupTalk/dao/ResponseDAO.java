package edu.upc.eetac.dsa.GroupTalk.dao;

import edu.upc.eetac.dsa.GroupTalk.entity.RelacionUserGrupo;
import edu.upc.eetac.dsa.GroupTalk.entity.Response;
import edu.upc.eetac.dsa.GroupTalk.entity.ResponseCollection;

import java.sql.SQLException;

/**
 * Created by Rosa on 25/03/16.
 */
public interface ResponseDAO {

    public Response createResponse (String idTema, String content, String autor_respuesta) throws SQLException;
    public Response updateResponse(String id, String content) throws SQLException;
    public Response getRespuestaById(String id) throws SQLException;
    public ResponseCollection getResponses(String idtema) throws SQLException;
    public boolean deleteResponse (String id) throws SQLException;

}
