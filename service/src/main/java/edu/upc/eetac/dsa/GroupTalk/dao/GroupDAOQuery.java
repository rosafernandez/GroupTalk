package edu.upc.eetac.dsa.GroupTalk.dao;

/**
 * Created by Rosa on 26/03/16.
 */
public interface GroupDAOQuery {

    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_GROUP = "insert into groups (id, nombre) values (UNHEX(?), ?)";
    public final static String GET_GROUPS="select hex(id) as id, nombre from groups";
    public final static String GET_GROUP_BY_ID = "select hex(g.id) as id, g.nombre from groups g where g.id=unhex(?) ";
   // public final static String GET_GROUP_BY_NAME = "select hex(g.id) as id, g.nombre from groups g where u.nombre=?";
    public final static String JOIN_GROUP = "insert into relacionUserGrupo (userid,groupid) values (UNHEX(?),UNHEX(?))";
    public final static String LEAVE_GROUP = "delete from relacionUserGrupo where userid=unhex(?) and groupid = unhex(?)";
}
   // select hex(g.id) as id, g.nombre, r.userid from groups g, relacionUserGrupo r where g.id=unhex(?) and r.groupid=g.id ";