package se.ifmo.refactoring.contacts.api;

import javax.ws.rs.*;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/api/contacts")
public interface ContactService {

  @GET
  @Produces(APPLICATION_JSON)
  List<Contact> getAllContacts(final @BeanParam ContactFilter contactFilter);

  @POST
  @Produces(APPLICATION_JSON)
  @Consumes(APPLICATION_JSON)
  Contact saveContact(final Contact contact);
}
