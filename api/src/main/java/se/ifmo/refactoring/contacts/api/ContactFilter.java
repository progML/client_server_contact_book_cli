package se.ifmo.refactoring.contacts.api;

import javax.ws.rs.QueryParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactFilter {

  @QueryParam("name")
  private String name;

  @QueryParam("surname")
  private String surname;

  @QueryParam("phone-number")
  private String phoneNumber;

  @QueryParam("email")
  private String email;
}
