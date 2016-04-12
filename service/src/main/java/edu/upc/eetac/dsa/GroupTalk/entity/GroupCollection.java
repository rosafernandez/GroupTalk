package edu.upc.eetac.dsa.GroupTalk.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.ws.rs.core.Link;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rosa on 26/03/16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupCollection {
    private List<Link> links;
    private List<Group> groups = new ArrayList<>();

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setStings(List<Group> groups) {
        this.groups = groups;
    }
}
