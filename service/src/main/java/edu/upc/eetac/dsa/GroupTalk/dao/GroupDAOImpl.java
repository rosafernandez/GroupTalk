package edu.upc.eetac.dsa.GroupTalk.dao;

import edu.upc.eetac.dsa.GroupTalk.entity.Group;
import edu.upc.eetac.dsa.GroupTalk.entity.GroupCollection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Rosa on 26/03/16.
 */
public class GroupDAOImpl implements GroupDAO {
    @Override
    public Group createGroup(String nombreGrupo) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        String id = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(GroupDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();

            connection.setAutoCommit(false);

            stmt.close();

            stmt = connection.prepareStatement(GroupDAOQuery.CREATE_GROUP);
            stmt.setString(1, id);
            stmt.setString(2, nombreGrupo);
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
        return getGroupById(id);
    }

    @Override
    public Group getGroupById(String id) throws SQLException {

        Group group = null;
        Connection connection = null;
        PreparedStatement stmt = null;
        try {

            connection = Database.getConnection();
            stmt = connection.prepareStatement(GroupDAOQuery.GET_GROUP_BY_ID);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                group = new Group();
                group.setId(rs.getString("id"));
                group.setNombreGrupo(rs.getString("nombre"));

            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return group;
    }

    @Override
    public GroupCollection getGroups() throws SQLException {
        GroupCollection groupCollection = new GroupCollection();

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(GroupDAOQuery.GET_GROUPS);

            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            while (rs.next()) {
                Group group = new Group();
                group.setId(rs.getString("id"));
                group.setNombreGrupo(rs.getString("nombre"));

                groupCollection.getGroups().add(group);
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return groupCollection;
    }

    @Override
    public boolean dejarGrupo(String userid, String groupid) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(GroupDAOQuery.LEAVE_GROUP);
            stmt.setString(1, userid);
            stmt.setString(2, groupid);

            int rows = stmt.executeUpdate();
            return (rows == 1);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
    }

    @Override
    public boolean unirseGrupo(String userid, String groupid) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(GroupDAOQuery.JOIN_GROUP);
            stmt.setString(1, userid);
            stmt.setString(2, groupid);

           int rows= stmt.executeUpdate();
            return (rows==1);

        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

    }

    /*public Group getGroupbyname(String name) throws SQLException {
        Group group = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(GroupDAOQuery.GET_GROUP_BY_NAME);
            stmt.setString(1,name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                group = new Group();
                group.setId(rs.getString("id"));
                group.setNombreGrupo(rs.getString("nombre"));

            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return group;
    }*/
}

