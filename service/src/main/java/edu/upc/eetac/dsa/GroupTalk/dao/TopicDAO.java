package edu.upc.eetac.dsa.GroupTalk.dao;

import edu.upc.eetac.dsa.GroupTalk.entity.RelacionUserGrupo;
import edu.upc.eetac.dsa.GroupTalk.entity.Topic;
import edu.upc.eetac.dsa.GroupTalk.entity.TopicCollection;

import java.sql.SQLException;

/**
 * Created by Rosa on 26/03/16.
 */
public interface TopicDAO {

    public Topic createTopic (String idAutor_tema, String subject, String content, String idgrupo) throws SQLException;
    public Topic getTopicById (String id) throws SQLException;
    public TopicCollection getTopics(String idgrupo) throws SQLException;
    public Topic updateTopic(String id, String titulo, String content) throws SQLException;
    public RelacionUserGrupo getRelacion(String userid, String groupid) throws SQLException;
    public boolean deleteTopic(String id) throws SQLException;
}