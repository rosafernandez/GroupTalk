package edu.upc.eetac.dsa.GroupTalk.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rosa on 26/03/16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)

public class TopicCollection {
    @InjectLinks({})
    private List<Link> links;
    private List<Topic> responses = new ArrayList<>();

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public List<Topic> getTopics() {
        return responses;
    }

    public void setTopics(List<Topic> topics) {
        this.responses = topics;
    }
}
