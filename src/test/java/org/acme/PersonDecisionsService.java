package org.acme;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.kie.kogito.incubation.common.MapDataContext;

import javax.ws.rs.POST;
import javax.ws.rs.Path;


@Path("/")
@RegisterRestClient(configKey = "decisionapi")
public interface PersonDecisionsService {

    @POST
    @Path("/decision")
    MapDataContext callCustomDecision(InputData payloadToSend);

    @POST
    @Path("/PersonDecisions")
    OutputData callGenericKogitoDecision(InputData payloadToSend);
}