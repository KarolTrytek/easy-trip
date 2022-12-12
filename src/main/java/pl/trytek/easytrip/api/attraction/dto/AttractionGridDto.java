package pl.trytek.easytrip.api.attraction.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import pl.trytek.easytrip.data.domain.AttractionTypeEnum;

import java.io.Serial;
import java.io.Serializable;

public record AttractionGridDto(

		@Schema(name = "Id atrakcji")
		Long id,

		@Schema(name = "Nazwa atrakcji")
		String name,

		@Schema(name = "Miejscowosc")
		String city,

		@Schema(name = "ulica")
		String street,

		@Schema(name = "Liczba polubien")
		Integer likes,

		@Schema(name = "czy darmowe")
		Boolean free,

		@Schema(name ="czy polubiona")
		Boolean like,

		@Schema(name = "Typ atrakcji")
		AttractionTypeEnum type

) implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
}