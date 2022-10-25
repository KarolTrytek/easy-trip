package pl.trytek.easytrip.api.attraction.dto;

import pl.trytek.easytrip.data.domain.AttractionTypeEnum;

import java.io.Serial;
import java.io.Serializable;

public record AttractionDto(

		Long id,

		String name,

		String city,

		Integer likes,

		AttractionTypeEnum type

) implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
}