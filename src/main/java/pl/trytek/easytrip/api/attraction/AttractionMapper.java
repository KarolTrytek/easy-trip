package pl.trytek.easytrip.api.attraction;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import pl.trytek.easytrip.api.attraction.dto.AttractionDetailsDto;
import pl.trytek.easytrip.api.attraction.dto.AttractionGridDto;
import pl.trytek.easytrip.data.domain.Attraction;
import pl.trytek.easytrip.data.domain.Favorite;
import pl.trytek.easytrip.data.domain.Like;

import java.util.List;

import static pl.trytek.easytrip.common.security.SecurityContextUtil.getLoggedUser;

@Mapper(componentModel = "spring")
@Component
public interface AttractionMapper {

	@Mapping(target = "name", source = "name")
	@Mapping(target = "type", source = "entity.type")
	@Mapping(target = "like", expression = "java(isLiked(entity.getLikes()))")
	@Mapping(target = "likes", expression = "java(mapLikes(entity.getLikes()))")
	AttractionGridDto map(Attraction entity);

	@Mapping(target = "name", source = "name")
	List<AttractionGridDto> map(List<Attraction> entities);

	@Mapping(target = "likes", expression = "java(mapLikes(entity.getLikes()))")
	@Mapping(target = "like", expression = "java(isLiked(entity.getLikes()))")
	@Mapping(target = "favourite", expression = "java(isFavourite(entity.getFavourites()))")
    AttractionDetailsDto mapForDetails(Attraction entity);

	default Integer mapLikes(List<Like> likes) {
		return likes.size();
	}
	
	default Boolean isLiked(List<Like> likes) {
		return likes.stream().anyMatch(like->like.getUser().getLogin().equals(getLoggedUser()));
	}
	default Boolean isFavourite(List<Favorite> favorites) {
		return favorites.stream().anyMatch(fav->fav.getUser().getLogin().equals(getLoggedUser()));
	}
}
