package se.ifmo.refactoring.contacts.server;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository
    extends JpaRepository<ContactEntity, Long>, JpaSpecificationExecutor<ContactEntity> {}
