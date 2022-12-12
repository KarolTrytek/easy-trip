package pl.trytek.easytrip.api.favourite;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.trytek.easytrip.api.favourite.dto.FavouriteAttractionDto;
import pl.trytek.easytrip.api.favourite.dto.FavouriteCityDto;
import pl.trytek.easytrip.common.exception.EasyTripApiException;
import pl.trytek.easytrip.common.exception.ExceptionCode;
import pl.trytek.easytrip.common.util.JPAUtils;
import pl.trytek.easytrip.data.domain.Attraction_;
import pl.trytek.easytrip.data.domain.Favorite;
import pl.trytek.easytrip.data.domain.Favorite_;
import pl.trytek.easytrip.data.domain.User_;
import pl.trytek.easytrip.data.repository.FavouriteRepository;

import javax.persistence.criteria.Predicate;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Slf4j
@Service
@RequiredArgsConstructor
public class FavouriteService {
    private final FavouriteRepository favouriteRepository;

    public Long editFavouriteAttractionNote(Long favouriteId, String note) {
        var favouriteAttraction = favouriteRepository.findById(favouriteId).orElseThrow(() -> new EasyTripApiException(ExceptionCode.VALIDATION_ERR, "Brak ulubionej atrakcji"));
//TODO sprawdzenie, czy favourite Id nalezy do zalogowango uzytkownika
        favouriteAttraction.setNote(note);

        return favouriteRepository.save(favouriteAttraction).getId();
    }

    public FavouriteAttractionDto getFavourite(Long favouriteId) {
        return favouriteRepository.findById(favouriteId)
                .map(fav -> new FavouriteAttractionDto(fav.getId(),
                        fav.getAttraction().getId(),
                        fav.getAttraction().getName(),
                        fav.getNote()))
                .orElseThrow(() -> new EasyTripApiException("Brak polubienia dla podanego id"));
    }

    public List<FavouriteCityDto> getFavourites(Principal principal) {

        List<FavouriteCityDto> favouriteCities = new ArrayList<>();

        var favourites = favouriteRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicatesList = new ArrayList<>();
            //JPAUtils.setEqualWithChild(root, criteriaBuilder, predicatesList, Favorite_.USER, User_.LOGIN, principal.getName());
            JPAUtils.setEqualWithChild(root, criteriaBuilder, predicatesList, Favorite_.USER, User_.ID, 2);
            Predicate[] predicates = predicatesList.toArray(new Predicate[0]);
            return criteriaBuilder.and(predicates);
        });
        var group = favourites.stream().collect(Collectors.groupingBy(fav -> fav.getAttraction().getCity()));

        for (Map.Entry<String, List<Favorite>> entry : group.entrySet()) {
            favouriteCities.add(new FavouriteCityDto(entry.getKey(), entry.getValue().size()));
        }

        return favouriteCities.stream().sorted(comparing(FavouriteCityDto::attractions).reversed()).toList();
    }

    public List<FavouriteAttractionDto> getFavouritesByCity(String city, Principal principal) {

        List<FavouriteAttractionDto> favouriteAttractions = new ArrayList<>();

        favouriteRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicatesList = new ArrayList<>();
            JPAUtils.setEqualWithChild(root, criteriaBuilder, predicatesList, Favorite_.ATTRACTION, Attraction_.CITY, city);
            //JPAUtils.setEqualWithChild(root, criteriaBuilder, predicatesList, Favorite_.USER, User_.LOGIN, principal.getName());
            JPAUtils.setEqualWithChild(root, criteriaBuilder, predicatesList, Favorite_.USER, User_.ID, 2);
            Predicate[] predicates = predicatesList.toArray(new Predicate[0]);
            return criteriaBuilder.and(predicates);
        }).forEach(fav -> favouriteAttractions.add(new FavouriteAttractionDto(fav.getAttraction().getId(), fav.getId(), fav.getAttraction().getName(), fav.getNote())));
        return favouriteAttractions;
    }

    public void deleteAttractionFromFavourites(Long favouriteId, Principal principal) {
//        var favouriteToDelete = favouriteRepository.findByIdAndUserLogin(favouriteId, principal.getName());
//        favouriteRepository.delete(favouriteToDelete);

        favouriteRepository.delete(favouriteRepository.findById(favouriteId).orElseThrow());
    }
}
