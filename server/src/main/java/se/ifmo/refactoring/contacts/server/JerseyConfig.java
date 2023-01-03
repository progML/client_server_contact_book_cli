package se.ifmo.refactoring.contacts.server;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.ifmo.refactoring.contacts.api.AuthService;
import se.ifmo.refactoring.contacts.api.ContactService;

@Configuration
public class JerseyConfig {

  @Bean
  public ResourceConfig jaxrsResourceConfig() {
    return new ResourceConfig()
            .register(ContactService.class)
            .register(AuthService.class)
            .register(SecurityFilter.class);
  }
}
