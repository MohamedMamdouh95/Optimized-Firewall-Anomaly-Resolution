package rest.resources;

import ofar.generated.classes.conflicts.Anomalies;
import ofar.generated.classes.conflicts.AnomalyNames;
import ofar.generated.classes.input.ServiceInput;
import ofar.generated.classes.rules.Rules;
import optimized.resolution.algorithm.classes.ConflictResolver;
import rest.resources.DB.Database;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;

@Path("/optimizer")
public class OptimizerResource {
    @POST
    @Path("/createResource")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
    public Response createOptimizer(ServiceInput serviceInput, @Context UriInfo uriInfo) {
        if(serviceInput == null){
            //throw new ForbiddenException("Empty Input");
        }
        ServiceInput savedInput = Database.insertEntry(serviceInput);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        URI u = builder.path(Long.toString(savedInput.getId())).build();
        return Response.created(u).entity(savedInput).build();
    }
    @GET
    @Path("/getConflictAnomalies/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Anomalies getUnresolvedAnomalies(@PathParam("id")int id){
        ServiceInput serviceInput= Database.getEntry(id);
        ConflictResolver conflictResolver = new ConflictResolver(serviceInput.getDefectedRules(),serviceInput.getAnomaliesList());
        return conflictResolver.getConflictAnomalies();
    }
    @GET
    @Path("/solveIrrelevance/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Rules solveIrrelevance (@PathParam("id")int id){
        ServiceInput serviceInput= Database.getEntry(id);
        ConflictResolver conflictResolver = new ConflictResolver(serviceInput.getDefectedRules(),serviceInput.getAnomaliesList());
        conflictResolver.removeIrrelevanceAnomaly();
        return conflictResolver.getRules();
    }
    @GET
    @Path("/solveDuplication/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Rules solveDuplication (@PathParam("id")int id){
        ServiceInput serviceInput= Database.getEntry(id);
        ConflictResolver conflictResolver = new ConflictResolver(serviceInput.getDefectedRules(),serviceInput.getAnomaliesList());
        conflictResolver.removeDuplicationOrShadowingRedundancyAnomaly(AnomalyNames.DUPLICATION);
        return conflictResolver.getRules();
    }
    @GET
    @Path("/solveShadowingRedundancy/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Rules solveShadowingRedundancy (@PathParam("id")int id){
        ServiceInput serviceInput= Database.getEntry(id);
        ConflictResolver conflictResolver = new ConflictResolver(serviceInput.getDefectedRules(),serviceInput.getAnomaliesList());
        conflictResolver.removeDuplicationOrShadowingRedundancyAnomaly(AnomalyNames.SHADOWING_REDUNDANCY);
        return conflictResolver.getRules();
    }

}



