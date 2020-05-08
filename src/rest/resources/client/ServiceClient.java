package rest.resources.client;

import data.serializer.DataUnmarshaller;
import ofar.generated.classes.input.ServiceInput;
import ofar.generated.classes.rules.Rules;
import ofar.generated.classes.solveRequest.SolveRequest;
import org.xml.sax.SAXException;
import rest.resources.OptimizerResource;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.JAXBException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceClient {
    Logger logger = Logger.getLogger(OptimizerResource.class.getName());
    private static Client client;

    private WebTarget target;        // the WebTarget of the main resource
    private Map<URI, ServiceInput> map;    // a local map to access created resources

    public ServiceClient() {

        // create the Client object
        client = ClientBuilder.newClient();

        // create a web target for the main URI
        target = client.target(getBaseURI());
        // create the map
        map = new HashMap<>();

    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost:8080/Optimized_Firewall_Anomaly_Resolution_war_exploded/rest/optimizer/").build();
    }

    private void runClient() throws JAXBException, SAXException {
        ServiceInput serviceInput = createType("src/data/serializer/serviceInput.xml", "xsd/webservice_input_schema.xsd", "ofar.generated.classes.input");
        performPost(serviceInput);
        performPost(serviceInput);
        performGetAllResources();
        performGetSingleResource(0, "");
        perfomPutOnSingleResource(0);
        performGetSingleResource(0, "");
        performGetSingleResource(1, "solveIrrelevance");

    }

    public URI performPost(ServiceInput serviceInput) {
        logger.log(Level.INFO, "--- Performing a Post --- \n");
        // Perform a POST http request using Json
        Response response = target.request(MediaType.APPLICATION_XML)
                .post(Entity.entity(serviceInput, MediaType.APPLICATION_XML));
        if (response.getStatus() == 201) {
            logger.log(Level.INFO, "--- Post executed correctly --- \n");
            // convert response entity to Negotiate object
            ServiceInput receivedServiceInput = response.readEntity(ServiceInput.class);
            URI uri = response.getLocation();
            logger.log(Level.INFO, "Received URI: " + uri);
            map.put(uri, receivedServiceInput);
            return uri;
        } else {
            logger.log(Level.INFO, "Post failed with status " + response.getStatus());
            return null;
        }
    }

    public void performGetSingleResource(Integer id, String action) {
        WebTarget itemTarget = null;
        ServiceInput serviceInput = null;
        logger.log(Level.INFO, " --- Performing GET operations on the single resource --- ");
        if (action.equals("")) {
            itemTarget = target.path(id.toString());
            serviceInput = itemTarget.request().accept(MediaType.APPLICATION_XML)
                    .get(ServiceInput.class);
        } else {
            if (action.equals("solveIrrelevance") ||
                    action.equals("solveDuplication") ||
                    action.equals("solveShadowingRedundancy") ||
                    action.equals("solveSub-optimization")) {
                itemTarget = target.queryParam(action).path(id.toString());
                serviceInput = itemTarget.request().accept(MediaType.APPLICATION_XML)
                        .get(ServiceInput.class);
            } else {
                logger.log(Level.SEVERE, "Incorrect GET Action");
            }
        }

        if (serviceInput == null) {
            logger.log(Level.INFO, "This item is not found");
        } else {
            logger.log(Level.INFO, serviceInput.getId().toString());
        }

    }

    public void perfomPutOnSingleResource(Integer id) throws JAXBException, SAXException {
        logger.log(Level.INFO, " --- Performing PUT operations on the single resource --- ");
        WebTarget itemTarget = target.path(id.toString());
        SolveRequest solveRequest = createType("xsd/solve_request.xml", "xsd/solve_request.xsd", "ofar.generated.classes.solveRequest");
        Rules rules = itemTarget.request().accept(MediaType.APPLICATION_XML).put(Entity.entity(solveRequest, MediaType.APPLICATION_XML), Rules.class);
        logger.log(Level.INFO, rules.getRule().toString());

    }

    public void performGetAllResources() {

        logger.log(Level.INFO, " --- Performing GET operations on the main resource --- ");

        // Get the response as json and convert it into list of Negotiate objects
        List<ServiceInput> xmlResponse = target.request().accept(MediaType.APPLICATION_XML)
                .get(new GenericType<List<ServiceInput>>() {
                });
        if (xmlResponse.size() == 0) {
            logger.log(Level.INFO, " LIST IS EMPTY ");

        } else {
            logger.log(Level.INFO, " --- Response of GET to all resources in the web service --- ");
            for (ServiceInput n : xmlResponse) {
                logger.log(Level.INFO, n.getId().toString());
                logger.log(Level.INFO, n.getDefectedRules().toString());
                logger.log(Level.INFO, n.getAnomaliesList().toString());
            }
        }


    }


    private <T> T createType(String fileName, String schemaPath, String contextPath) throws JAXBException, SAXException {
        return DataUnmarshaller.unmarshallSolveRequest(fileName, schemaPath, contextPath);
    }

    public static void main(String[] args) throws JAXBException, SAXException {
        ServiceClient serviceClient = new ServiceClient();
        serviceClient.runClient();
    }


}
