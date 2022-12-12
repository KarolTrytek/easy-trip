package pl.trytek.easytrip.api.favourite;

import lombok.AllArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import pl.trytek.easytrip.api.favourite.dto.FavouriteAttractionDto;
import pl.trytek.easytrip.api.favourite.dto.FavouriteCityDto;
import pl.trytek.easytrip.common.response.JsonResponse;
import pl.trytek.easytrip.common.response.JsonResponseBuilder;

import java.security.Principal;
import java.util.List;

import static pl.trytek.easytrip.common.util.StringUtils.API;

@AllArgsConstructor
@RestController
@CrossOrigin
@RequestMapping(API + "favourite")
public class FavouriteController {

    private final FavouriteService favouriteService;

    private static final String SWAGGER_TAG = "Zarządzanie sekcją ulubione";


    @GetMapping("list")
    public JsonResponse<List<FavouriteCityDto>> getFavourites(Principal principal) {
        return JsonResponseBuilder.ok(favouriteService.getFavourites(principal));
    }

    @GetMapping("city/{city}")
    public JsonResponse<List<FavouriteAttractionDto>> getFavouritesByCity(@PathVariable String city, Principal principal) {
        return JsonResponseBuilder.ok(favouriteService.getFavouritesByCity(city,principal));
    }

    @GetMapping("{favouriteId}")
    public JsonResponse<FavouriteAttractionDto> getFavourite(@PathVariable Long favouriteId) {
        return JsonResponseBuilder.ok(favouriteService.getFavourite(favouriteId));
    }


    @PutMapping("{favouriteId}")
    public JsonResponse<Long> editFavouriteAttractionNote(@PathVariable Long favouriteId, @ParameterObject String note) {
        return JsonResponseBuilder.ok(favouriteService.editFavouriteAttractionNote(favouriteId, note));
    }

    @DeleteMapping("{favouriteId}")
    public JsonResponse<Void> deleteAttractionFromFavourites(@PathVariable Long favouriteId, Principal principal) {
        favouriteService.deleteAttractionFromFavourites(favouriteId, principal);
        return JsonResponseBuilder.ok();
    }
}
