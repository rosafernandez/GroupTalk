package edu.upc.eetac.dsa.GroupTalk.dao;

/**
 * Created by Rosa on 25/03/16.
 */
public interface ResponseDAOQuery {
    public final static String  UUID = "select REPLACE(UUID(),'-','')";

    public final static String CREATE_RESPONSE = "insert into respuesta (id, idtema,autor_respuesta, content) " +
            "values (UNHEX(?), UNHEX(?),UNHEX(?), ?)";

    public final static String GET_RESPONSE="select hex(id) as id, hex(idtema) as idtema, hex(autor_respuesta) as "+
            "autor_respuesta,content from respuesta where idtema = unhex(?)";

    public final static String GET_RESPONSE_BY_ID= "select hex(r.id) as id, hex(r.idtema) as idtema, hex(r.autor_respuesta) " +
            "as autor_respuesta, r.content, u.loginid from respuesta r, users u where r.id=unhex(?)";


    public final static String UPDATE_RESPONSE="update respuesta content=? where id=unhex(?) ";

    public final static String DELETE_RESPONSE="delete from respuesta where id=unhex(?)";


}
