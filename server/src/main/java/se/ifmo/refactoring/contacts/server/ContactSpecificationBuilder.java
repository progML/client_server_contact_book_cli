package se.ifmo.refactoring.contacts.server;

import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import se.ifmo.refactoring.contacts.api.ContactFilter;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ContactSpecificationBuilder {

  public static Specification<ContactEntity> withName(final @Nullable String name) {
    return ((root, query, criteriaBuilder) -> {
      if (name == null) {
        return criteriaBuilder.conjunction();
      }
      return criteriaBuilder.like(
          criteriaBuilder.lower(root.get(ContactEntity_.NAME)), "%" + name.toLowerCase() + "%");
    });
  }

  public static Specification<ContactEntity> withSurname(final @Nullable String surname) {
    return ((root, query, criteriaBuilder) -> {
      if (surname == null) {
        return criteriaBuilder.conjunction();
      }
      return criteriaBuilder.like(
          criteriaBuilder.lower(root.get(ContactEntity_.SURNAME)),
          "%" + surname.toLowerCase() + "%");
    });
  }

  public static Specification<ContactEntity> withPhoneNumber(final @Nullable String phoneNumber) {
    return ((root, query, criteriaBuilder) -> {
      if (phoneNumber == null) {
        return criteriaBuilder.conjunction();
      }
      return criteriaBuilder.like(
          criteriaBuilder.lower(root.get(ContactEntity_.PHONE_NUMBER)),
          "%" + phoneNumber.toLowerCase() + "%");
    });
  }

  public static Specification<ContactEntity> withEmail(final @Nullable String email) {
    return ((root, query, criteriaBuilder) -> {
      if (email == null) {
        return criteriaBuilder.conjunction();
      }
      return criteriaBuilder.like(
          criteriaBuilder.lower(root.get(ContactEntity_.EMAIL)), "%" + email.toLowerCase() + "%");
    });
  }

  public static Specification<ContactEntity> fromContactFilter(
      final @Nullable ContactFilter contactFilter) {
    if (contactFilter == null) {
      return Specification.where(null);
    }

    if (contactFilter.getName() != null && contactFilter.getSurname() != null) {
      return Specification.where(withName(contactFilter.getName()))
          .or(withSurname(contactFilter.getSurname()));
    }

    return Specification.where(withName(contactFilter.getName()))
        .and(withSurname(contactFilter.getSurname()))
        .and(withPhoneNumber(contactFilter.getPhoneNumber()))
        .and(withEmail(contactFilter.getEmail()));
  }
}
