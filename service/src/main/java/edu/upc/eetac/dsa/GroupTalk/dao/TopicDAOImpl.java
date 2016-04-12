package edu.upc.eetac.dsa.GroupTalk.dao;

import edu.upc.eetac.dsa.GroupTalk.entity.RelacionUserGrupo;
import edu.upc.eetac.dsa.GroupTalk.entity.Topic;
import edu.upc.eetac.dsa.GroupTalk.entity.TopicCollection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Rosa on 26/03/16.
 */
public class TopicDAOImpl implements TopicDAO {

    @Override
    public Topic createTopic (String autor_tema, String subject, String content, String idgrupo) throws SQLException{
        Connection connection = null;
        PreparedStatement stmt = null;
        String id = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(TopicDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();

            connection.setAutoCommit(false);

            stmt.close();

            stmt = connection.prepareStatement(TopicDAOQuery.CREATE_TOPIC);
            stmt.setString(1, id);
            stmt.setString(2, idgrupo);
            stmt.setString(3, autor_tema);
            stmt.setString(4,subject);
            stmt.setString(5,content);
            stmt.executeUpdate();
            stmt.close();

            connection.commit();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return getTopicById(id);
    }
    @Override
    public Topic getTopicById (String id) throws SQLException{
        Topic topic = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(TopicDAOQuery.GET_TOPIC_BY_ID);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                topic = new Topic();
                topic.setId(rs.getString("id"));
                topic.setIdGrupo(rs.getString("idgrupo"));
                topic.setAutor_tema(rs.getString("autor_tema"));
                topic.setTitulo(rs.getString("titulo"));
                topic.setContent(rs.getString("content"));


            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return topic;

    }

    @Override
    public TopicCollection getTopics(String idgrupo) throws SQLException{
        TopicCollection topicCollection = new TopicCollection();

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(TopicDAOQuery.GET_TOPICS);
            stmt.setString(1,idgrupo);
            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            while (rs.next()) {
                Topic topic = new Topic();
                topic.setId(rs.getString("id"));
                topic.setIdGrupo(rs.getString("idgrupo"));
                topic.setAutor_tema(rs.getString("autor_tema"));
                topic.setTitulo(rs.getString("titulo"));
                topic.setContent(rs.getString("content"));
                topicCollection.getTopics().add(topic);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return topicCollection;
    }

    @Override
    public Topic updateTopic(String id, String titulo, String content) throws SQLException {
        Topic topic = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(TopicDAOQuery.UPDATE_TOPIC);
            stmt.setString(1, titulo);
            stmt.setString(2, content);
            stmt.setString(3, id);

            int rows = stmt.executeUpdate();
            if (rows == 1)
                topic = getTopicById(id);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return topic;
    }

    @Override
    public boolean deleteTopic(String id) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(TopicDAOQuery.DELETE_TOPIC);
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


    @Override
    public RelacionUserGrupo getRelacion(String userid, String groupid) throws SQLException {

        RelacionUserGrupo relacionUserGrupo = null;
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(TopicDAOQuery.GET_RELACION);
            stmt.setString(1, userid);
            stmt.setString(2, groupid);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                relacionUserGrupo = new RelacionUserGrupo();
                relacionUserGrupo.setUserid(rs.getString("userid"));
                relacionUserGrupo.setGroupid(rs.getString("groupid"));
            }
        } catch (SQLException e){
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return  relacionUserGrupo;
    }

}
