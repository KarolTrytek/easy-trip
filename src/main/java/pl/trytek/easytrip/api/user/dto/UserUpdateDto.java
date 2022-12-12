package pl.trytek.easytrip.api.user.dto;

import pl.trytek.easytrip.data.domain.UserRoleEnum;

import java.io.Serial;
import java.io.Serializable;

public record UserUpdateDto(

        String login,

        UserRoleEnum role

) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
