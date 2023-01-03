package se.ifmo.refactoring.contacts.client;

import se.ifmo.refactoring.contacts.api.Contact;
import se.ifmo.refactoring.contacts.api.ContactFilter;
import se.ifmo.refactoring.contacts.api.ContactService;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;

public class ContactClient implements ContactService {

  private final WebTarget webTarget;
  private final String basicAuth;
  private static final String AUTHORIZATION_KEY = "Authorization";

  public ContactClient(final String host, final String basicAuth) {
    this.webTarget = ClientBuilder.newClient().target(host + "/api/contacts");
    this.basicAuth = basicAuth;
  }

  @Override
  public List<Contact> getAllContacts(final ContactFilter contactFilter) {
    return webTarget
        .queryParam("name", contactFilter.getName())
        .queryParam("surname", contactFilter.getSurname())
        .queryParam("phone-number", contactFilter.getPhoneNumber())
        .queryParam("email", contactFilter.getEmail())
        .request(APPLICATION_JSON_TYPE)
            .header(AUTHORIZATION_KEY, basicAuth)
        .get(new GenericType<List<Contact>>() {});
  }

  @Override
  public Contact saveContact(final Contact contact) {
    return webTarget.request(APPLICATION_JSON_TYPE).header(AUTHORIZATION_KEY, basicAuth).post(Entity.json(contact), Contact.class);
  }
}
