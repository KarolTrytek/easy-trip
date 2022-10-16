package pl.trytek.easytrip.api.attraction;

import java.io.Serial;
import java.io.Serializable;

public record AttractionDto(

		Long id,

		String name,

		String description,

		String city

) implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
}