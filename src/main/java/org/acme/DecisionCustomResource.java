package org.acme;

import io.quarkus.logging.Log;
import org.kie.kogito.incubation.application.AppRoot;
import org.kie.kogito.incubation.common.MapDataContext;
import org.kie.kogito.incubation.decisions.DecisionIds;
import org.kie.kogito.incubation.decisions.services.DecisionService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Map;

@Path("/decision")
public class DecisionCustomResource {

    // Inject the application root
    @Inject
    AppRoot appRoot;
    // Inject a decision service
    @Inject
    DecisionService svc;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public MapDataContext customDecision(Map<String, Object> payload) {
        Log.info("/decision called " + payload);
        var id = appRoot
                .get(DecisionIds.class)
                .get("https://kiegroup.org/dmn/rest-example",
                        "PersonDecisions");
        var ctx = MapDataContext.from(payload);
        MapDataContext r = svc.evaluate(id, ctx).data().as(MapDataContext.class);
        Log.info(r);
        return r;
    }
}
