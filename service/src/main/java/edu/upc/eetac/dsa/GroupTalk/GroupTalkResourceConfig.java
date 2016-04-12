package edu.upc.eetac.dsa.GroupTalk;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

/**
 * Created by Rosa on 27/03/16.
 */
public class GroupTalkResourceConfig extends ResourceConfig {
    public GroupTalkResourceConfig() {
        packages("edu.upc.eetac.dsa.GroupTalk");
        packages("edu.upc.eetac.dsa.beeter.auth");
        register(RolesAllowedDynamicFeature.class);
    }
}
