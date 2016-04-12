package edu.upc.eetac.dsa.GroupTalk.dao;

import edu.upc.eetac.dsa.GroupTalk.entity.Group;
import edu.upc.eetac.dsa.GroupTalk.entity.GroupCollection;

import java.sql.SQLException;

/**
 * Created by Rosa on 26/03/16.
 */
public interface GroupDAO {
    public Group createGroup(String nombreGrupo) throws SQLException;

    public Group getGroupById(String id) throws SQLException;

    public GroupCollection getGroups() throws SQLException;

    public boolean dejarGrupo(String userid, String groupid) throws SQLException;

    public boolean unirseGrupo(String userid, String groupid) throws SQLException;
}
