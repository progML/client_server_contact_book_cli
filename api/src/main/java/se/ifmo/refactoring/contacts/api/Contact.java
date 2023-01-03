package se.ifmo.refactoring.contacts.api;

import lombok.Data;

@Data
public class Contact {

  private Long id;

  private String name;

  private String surname;

  private String phoneNumber;

  private String email;
}
