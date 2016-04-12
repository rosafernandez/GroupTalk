package edu.upc.eetac.dsa.GroupTalk.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.glassfish.jersey.linking.InjectLinks;

/**
 * Created by Rosa on 26/03/16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupTalkError {
    @InjectLinks({})
    private int status;
    private String reason;

    public GroupTalkError() {
    }

    public GroupTalkError(int status, String reason) {
        this.status = status;
        this.reason = reason;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
