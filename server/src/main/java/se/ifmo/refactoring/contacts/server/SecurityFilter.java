package se.ifmo.refactoring.contacts.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;

@Provider
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class SecurityFilter implements ContainerRequestFilter {

    private static final String AUTHORIZATION_KEY = "Authorization";
    private static final String AUTHORIZATION_KEY_PREFIX = "Basic ";
    private static final String LOGIN_PATH = "login";
    private static final String REGISTER_PATH = "register";

    private final UserRepository userRepository;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        if (!containerRequestContext.getUriInfo().getPath().contains(LOGIN_PATH) && !containerRequestContext.getUriInfo().getPath().contains(REGISTER_PATH)) {
            List<String> authHeaders = containerRequestContext.getHeaders().get(AUTHORIZATION_KEY);
            if (authHeaders != null && authHeaders.size() > 0) {
                String authToken = authHeaders.get(0);
                authToken = authToken.replaceFirst(AUTHORIZATION_KEY_PREFIX, "");
                String decodeString = new String(Base64.getDecoder().decode(authToken));
                StringTokenizer tokenizer = new StringTokenizer(decodeString, ":");
                String login = tokenizer.nextToken();
                String password = tokenizer.nextToken();
                Optional<UserEntity> dbUser = userRepository.findByLogin(login);
                if (dbUser.isPresent() && password.equals(dbUser.get().getPassword())){
                    return;
                }
            }
            Response unauthorizedResponse = Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity("Unauthorized user")
                    .build();
            containerRequestContext.abortWith(unauthorizedResponse);
        }
    }
}
