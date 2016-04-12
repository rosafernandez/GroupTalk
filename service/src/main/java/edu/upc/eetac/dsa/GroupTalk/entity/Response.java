package edu.upc.eetac.dsa.GroupTalk.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.List;

/**
 * Created by Rosa on 25/03/16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    @InjectLinks({})
    private List<Link> links;
    private String id;
    private String idtema;
    private String content;

    private String autor_respuesta;

    public String getAutor_respuesta() {
        return autor_respuesta;
    }

    public void setAutor_respuesta(String autor_respuesta) {
        this.autor_respuesta = autor_respuesta;
    }


    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdtema() {
        return idtema;
    }

    public void setIdtema(String idtema) {
        this.idtema = idtema;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
