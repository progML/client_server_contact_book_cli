package se.ifmo.refactoring.contacts.api;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/api/auth")
public interface AuthService {

    @POST
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    @Path("/register")
    boolean register(final User user);

    @POST
    @Path("/login")
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    Response login(final User user);
}
