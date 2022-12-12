package pl.trytek.easytrip.api.favourite.dto;

import java.io.Serial;
import java.io.Serializable;

public record FavouriteCityDto(

        String city,

        Integer attractions

) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}