package pl.trytek.easytrip.api.favourite.dto;

import java.io.Serial;
import java.io.Serializable;

public record FavouriteAttractionDto(

        Long attractionId,

        Long favouriteId,

        String attractionName,

        String note
)implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
