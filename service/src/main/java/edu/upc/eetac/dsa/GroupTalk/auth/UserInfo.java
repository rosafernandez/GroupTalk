package edu.upc.eetac.dsa.GroupTalk.auth;

//import edu.upc.eetac.dsa.beeter.entity.Role;
import edu.upc.eetac.dsa.GroupTalk.entity.Role;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rosa on 26/03/16.
 */
public class UserInfo implements Principal {
    private String name;
    private List<Role> roles = new ArrayList<>();

    public UserInfo() {
    }

    public UserInfo(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}

