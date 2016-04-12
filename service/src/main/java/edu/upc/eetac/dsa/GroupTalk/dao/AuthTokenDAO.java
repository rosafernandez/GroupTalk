package edu.upc.eetac.dsa.GroupTalk.dao;

import edu.upc.eetac.dsa.GroupTalk.auth.UserInfo;
import edu.upc.eetac.dsa.GroupTalk.entity.AuthToken;

import java.sql.SQLException;

/**
 * Created by Rosa on 26/03/16.
 */
public interface AuthTokenDAO {
    public UserInfo getUserByAuthToken(String token) throws SQLException;
    public AuthToken createAuthToken(String userid) throws SQLException;
    public void deleteToken(String userid) throws  SQLException;
}
