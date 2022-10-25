package pl.trytek.easytrip.api.registration;

import lombok.NonNull;

public record RegistrationDto(

        @NonNull
        String username,

        @NonNull
        String password
) {
}
