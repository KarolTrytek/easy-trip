package pl.trytek.easytrip.api.attraction;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import pl.trytek.easytrip.data.domain.Attraction;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface AttractionMapper {

	@Mapping(target = "name", source = "name")
	AttractionDto map(Attraction entity);

	@Mapping(target = "name", source = "name")
	List<AttractionDto> map(List<Attraction> entities);
}
