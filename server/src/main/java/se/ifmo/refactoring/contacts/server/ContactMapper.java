package se.ifmo.refactoring.contacts.server;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import se.ifmo.refactoring.contacts.api.Contact;

@Mapper(componentModel = "spring")
public interface ContactMapper {

  Contact mapToDto(final ContactEntity contactEntity);

  ContactEntity mapToEntity(final Contact contact);

  void updateEntityFromDto(final Contact contact, final @MappingTarget ContactEntity contactEntity);
}
