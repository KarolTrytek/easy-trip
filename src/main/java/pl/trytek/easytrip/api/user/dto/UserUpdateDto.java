package pl.trytek.easytrip.api.user.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

public record UserUpdateDto(

        Long userId,

        Set<String> roles

) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
