package pl.trytek.easytrip.api.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import pl.trytek.easytrip.api.user.dto.UserDto;
import pl.trytek.easytrip.data.domain.User;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {

    @Mapping(target = "login", source = "entity.login")
    UserDto map(User entity);

    List<UserDto> map(List<User> entities);
}
