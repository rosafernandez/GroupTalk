package edu.upc.eetac.dsa.GroupTalk.dao;

import edu.upc.eetac.dsa.GroupTalk.entity.Response;
import edu.upc.eetac.dsa.GroupTalk.entity.ResponseCollection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Rosa on 25/03/16.
 */
public class ResponseDAOImpl implements ResponseDAO {
    @Override
    public Response createResponse (String idTema, String content, String autor_respuesta) throws SQLException{
        Connection connection = null;
        PreparedStatement stmt = null;
        String id = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(ResponseDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();

            connection.setAutoCommit(false);

            stmt.close();

            stmt = connection.prepareStatement(ResponseDAOQuery.CREATE_RESPONSE);
            stmt.setString(1, id);
            stmt.setString(2, idTema);
            stmt.setString(3, autor_respuesta);
            stmt.setString(4,content);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return getRespuestaById(id);
    }
    @Override
    public Response updateResponse(String id, String content) throws SQLException{
        Response response = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(ResponseDAOQuery.UPDATE_RESPONSE);
            stmt.setString(1, id);
            stmt.setString(2, content);

            int rows = stmt.executeUpdate();
            if (rows == 1)
                response = getRespuestaById(id);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return response;
    }

    @Override
    public Response getRespuestaById(String id) throws SQLException{
        Response response = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(ResponseDAOQuery.GET_RESPONSE_BY_ID);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                response = new Response();
                response.setId(rs.getString("id"));
                response.setIdtema(rs.getString("idtema"));
                response.setAutor_respuesta(rs.getString("autor_respuesta"));
                response.setContent(rs.getString("content"));


            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return response;

    }
    @Override
    public ResponseCollection getResponses(String idtema) throws SQLException{

        ResponseCollection responseCollection = new ResponseCollection();

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(ResponseDAOQuery.GET_RESPONSE);
            stmt.setString(1,idtema);
            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            while (rs.next()) {
                Response response = new Response();
                response.setId(rs.getString("id"));
                response.setIdtema(rs.getString("idtema"));
                response.setAutor_respuesta(rs.getString("autor_respuesta"));
                response.setContent(rs.getString("content"));

                responseCollection.getResponses().add(response);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return responseCollection;
    }
    @Override
    public boolean deleteResponse (String id) throws SQLException{
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(ResponseDAOQuery.DELETE_RESPONSE);
            stmt.setString(1, id);

            int rows = stmt.executeUpdate();
            return (rows == 1);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null)
                stmt.close();
            if (connection != null)
                connection.close();
        }
    }
}
