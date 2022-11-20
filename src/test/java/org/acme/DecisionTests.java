package org.acme;

import io.quarkus.logging.Log;
import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;
import org.kie.kogito.incubation.application.AppRoot;
import org.kie.kogito.incubation.common.ExtendedDataContext;
import org.kie.kogito.incubation.common.MapDataContext;
import org.kie.kogito.incubation.decisions.DecisionIds;
import org.kie.kogito.incubation.decisions.services.DecisionService;

import javax.inject.Inject;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@QuarkusTest
public class DecisionTests {

    @Inject
    @RestClient
    PersonDecisionsService decisionApi;


    @Test
    public void testExampleCustomRestAPI() {
        InputData i = InputData.builder().age(23).visits(11).build();
        MapDataContext r = decisionApi.callCustomDecision(i);
        Log.info(r);
        if (r.get("percent") != null && r.get("percent") instanceof Double) {
            assertTrue(((Double) r.get("percent")).floatValue() != 0);
        } else {
            fail("invalid decision model response");
        }
    }

    @Test
    public void testExampleKogitoGenericRestAPI() {
        InputData i = InputData.builder().age(2).visits(11).build();
        OutputData r = decisionApi.callGenericKogitoDecision(i);
        Log.info(r);
        assertTrue(r.percent != 0);
    }

    // Inject the application root
    @Inject
    AppRoot appRoot;
    // Inject a decision service
    @Inject
    DecisionService svc;

    @Test
    public void testExampleDirectKogitoAPI() {
        var payload = InputData.builder().age(2).visits(11).build();
        var id = appRoot
                .get(DecisionIds.class)
                .get("https://kiegroup.org/dmn/rest-example",
                        "PersonDecisions");
        var ctx = MapDataContext.from(payload);
        ExtendedDataContext edc = svc.evaluate(id, ctx);
        Log.info(edc.data());
        if (edc.data().as(MapDataContext.class).get("percent") instanceof BigDecimal) {
            BigDecimal f = (BigDecimal) edc.data().as(MapDataContext.class).get("percent");
            assertTrue(f.floatValue() > 0);
        } else {
            fail("invalid return type");
        }
    }
}
