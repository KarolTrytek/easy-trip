package pl.trytek.easytrip.api.attraction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.trytek.easytrip.api.attraction.dto.AttractionCriteriaDto;
import pl.trytek.easytrip.api.attraction.dto.AttractionDetailsDto;
import pl.trytek.easytrip.api.attraction.dto.AttractionGridDto;
import pl.trytek.easytrip.api.attraction.dto.CountryDto;
import pl.trytek.easytrip.common.exception.EasyTripApiException;
import pl.trytek.easytrip.common.exception.ExceptionCode;
import pl.trytek.easytrip.common.util.JPAUtils;
import pl.trytek.easytrip.data.domain.*;
import pl.trytek.easytrip.data.repository.*;

import javax.persistence.criteria.Predicate;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttractionService {
    private final AttractionRepository attractionRepository;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final FavouriteRepository favouriteRepository;
    private final AttractionMapper mapper;

    @Transactional
    public List<AttractionGridDto> getAttractions() {
        return mapper.map(attractionRepository.findAll());
    }

    public List<CountryDto> getCountries() {
        return countryRepository.findAll().stream().map(c -> new CountryDto(c.getId(), c.getName())).toList();
    }

    public List<AttractionGridDto> getAttractions(AttractionCriteriaDto criteria) {

        var attractions = attractionRepository.findAll((root, query, cb) -> {
            List<Predicate> predicatesList = new ArrayList<>();
            JPAUtils.setEqualOrLikeParamWithIgnoreCase(root, cb, predicatesList, Attraction_.CITY, criteria.city());
            JPAUtils.setEqualWithChild(root, cb, predicatesList, Attraction_.COUNTRY, Country_.NAME, criteria.country());
            JPAUtils.setEqualOrLikeParamWithIgnoreCase(root, cb, predicatesList, Attraction_.NAME, criteria.name());

            return cb.and(predicatesList.toArray(new Predicate[0]));
        });

        if (attractions.isEmpty()) {
            return null;
        } else {
            return mapper.map(attractions);
        }

    }

    public AttractionDetailsDto getAttractionDetails(Long id) {
        return mapper.mapForDetails(attractionRepository.findOneById(id));

    }

    public Long likeUnlikeAttraction(Long attractionId, Principal principal) {

        var attraction = attractionRepository.findOneById(attractionId);

        User user = userRepository.findOneByLogin(principal.getName());

        var optLike = likeRepository.findByAttractionAndUser(attraction, user);
        if (optLike.isPresent()) {
            likeRepository.delete(optLike.get());
            return null;
        } else {
            return likeRepository.save(new Like(attraction, user)).getId();
        }
    }

    public Long addAttractionToFavourite(Long attractionId, Principal principal) {

        var attraction = attractionRepository.findOneById(attractionId);
        var user = userRepository.findOneByLogin(principal.getName());

        var optFavourite = favouriteRepository.findByAttractionAndUser(attraction, user);
        if (optFavourite.isPresent()) {
            throw new EasyTripApiException(ExceptionCode.VALIDATION_ERR, "Podana atrakcja jest w ulubionych");
        }

        return favouriteRepository.save(Favorite.builder().attraction(attraction).user(user).build()).getId();
    }

    public String addAttraction(AttractionCriteriaDto criteria) {
        return null;
    }

    public void deleteAttraction(Long attractionId) {
        attractionRepository.deleteById(attractionId);

        var favouritesToDelete = favouriteRepository.findAllByAttractionId(attractionId);

        favouriteRepository.deleteAll(favouritesToDelete);
    }
}
