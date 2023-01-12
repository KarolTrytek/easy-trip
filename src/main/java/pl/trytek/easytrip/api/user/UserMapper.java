package pl.trytek.easytrip.api.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import pl.trytek.easytrip.api.user.dto.UserDto;
import pl.trytek.easytrip.data.domain.User;
import pl.trytek.easytrip.data.domain.UserRole;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {

    @Mapping(target = "login", source = "entity.login")
    @Mapping(target = "roles", expression = "java(mapUserRolesToNames(entity.getRoles()))")
    UserDto map(User entity);

    List<UserDto> map(List<User> entities);

    default List<String> mapUserRolesToNames(Set<UserRole> roles) {
        return roles.stream().map(role->role.getRole().getName()).sorted().toList();
    }
}
