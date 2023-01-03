package se.ifmo.refactoring.contacts.client;

import se.ifmo.refactoring.contacts.api.AuthService;
import se.ifmo.refactoring.contacts.api.User;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class AuthClient implements AuthService {

    private final WebTarget webTarget;

    public AuthClient(final String host) {
        this.webTarget = ClientBuilder.newClient().target(host + "/api/auth");
    }

    @Override
    public boolean register(final User user) {
        return webTarget
                .path("register")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(user), Boolean.class);
    }

    @Override
    public Response login(final User user) {
        return webTarget
                .path("login")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(user));
    }
}
