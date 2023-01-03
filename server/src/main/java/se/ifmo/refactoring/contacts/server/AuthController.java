package se.ifmo.refactoring.contacts.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.ifmo.refactoring.contacts.api.AuthService;
import se.ifmo.refactoring.contacts.api.User;

import javax.ws.rs.core.Response;
import java.util.Base64;
import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class AuthController implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public boolean register(final User user) {
        log.info("POST register user = {}", user);
        final var entity = userMapper.mapToEntity(user);
        Optional<UserEntity> dbUser = userRepository.findByLogin(entity.getLogin());
        if (dbUser.isPresent()){
            return false;
        }
        userRepository.save(entity);
        return true;
    }

    @Override
    public Response login(User user) {
        log.info("POST login user = {}", user);
        final var entity = userMapper.mapToEntity(user);
        Optional<UserEntity> dbUser = userRepository.findByLogin(entity.getLogin());
        if (dbUser.isPresent() && entity.getPassword().equals(dbUser.get().getPassword())){
            String original = entity.getLogin() + ":" + entity.getPassword();
            String basicAuth = "Basic " + Base64.getEncoder().encodeToString(original.getBytes());
            return Response.ok().header("Authorization", basicAuth).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
