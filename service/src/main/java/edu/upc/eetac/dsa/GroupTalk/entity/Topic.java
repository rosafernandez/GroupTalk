package edu.upc.eetac.dsa.GroupTalk.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.List;

/**
 * Created by Rosa on 26/03/16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Topic {
    @InjectLinks({})
    private List<Link> links;
    private String id;
    private String titulo;
    private String content;
    private String idGrupo;

    private String autor_tema;

    public String getAutor_tema() {
        return autor_tema;
    }

    public void setAutor_tema(String autor_tema) {
        this.autor_tema = autor_tema;
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(String idGrupo) {
        this.idGrupo = idGrupo;
    }
}
