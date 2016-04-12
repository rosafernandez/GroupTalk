package edu.upc.eetac.dsa.GroupTalk.dao;


import edu.upc.eetac.dsa.GroupTalk.entity.Topic;

/**
 * Created by Rosa on 26/03/16.
 */
public interface TopicDAOQuery {
    public final static String  UUID = "select REPLACE(UUID(),'-','')";

    public final static String CREATE_TOPIC = "insert into tema (id, idgrupo,autor_tema, titulo, content) " +
            "values (UNHEX(?), unhex(?),unhex(?), ?, ?)";

    public final static String GET_TOPIC_BY_ID = "select hex(t.id) as id, hex(t.idgrupo) as idgrupo," +
            " hex(t.autor_tema) as autor_tema, t.titulo, t.content, u.loginid from tema t, groups g," +
            "users u where t.id=unhex(?)";

    public final static String GET_TOPICS = "select hex(id) as id, hex(idgrupo) as idgrupo, hex(autor_tema) as " +
            "autor_tema,titulo,content from tema where idgrupo = unhex(?) ";

    public final static String UPDATE_TOPIC = "update tema set titulo=?, content=? where id=unhex(?) ";

    public final static String DELETE_TOPIC = "delete from tema where id=unhex(?)";

    public final static String GET_RELACION= "select hex(r.groupid) as groupid, hex(r.userid) as userid from relacionUserGrupo r where r.userid=unhex(?) and r.groupid=unhex(?)";
}
