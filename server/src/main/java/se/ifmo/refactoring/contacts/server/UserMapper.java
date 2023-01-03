package se.ifmo.refactoring.contacts.server;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import se.ifmo.refactoring.contacts.api.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User mapToDto(final UserEntity userEntity);
    UserEntity mapToEntity(final User user);
    void updateEntityFromDto(final User user, final @MappingTarget UserEntity userEntity);

}
