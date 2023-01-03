package se.ifmo.refactoring.contacts.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import se.ifmo.refactoring.contacts.api.Contact;
import se.ifmo.refactoring.contacts.api.ContactFilter;
import se.ifmo.refactoring.contacts.api.ContactService;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class ContactController implements ContactService {

  private final ContactRepository contactRepository;
  private final ContactMapper contactMapper;

  @Override
  public List<Contact> getAllContacts(final ContactFilter contactFilter) {
    log.info("GET all by contactFilter = {}", contactFilter);

    final Specification<ContactEntity> contactSpecification =
        ContactSpecificationBuilder.fromContactFilter(contactFilter);
    Sort.sort(ContactEntity.class);
    return contactRepository.findAll(contactSpecification, Sort.by(ContactEntity_.ID)).stream()
        .map(contactMapper::mapToDto)
        .collect(Collectors.toList());
  }

  @Override
  public Contact saveContact(final Contact contact) {
    log.info("POST contact = {}", contact);

    final var entity = contactMapper.mapToEntity(contact);
    return contactMapper.mapToDto(contactRepository.save(entity));
  }
}
