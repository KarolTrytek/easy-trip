package pl.trytek.easytrip.api.favourite;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import pl.trytek.easytrip.api.favourite.dto.FavouriteAttractionDto;
import pl.trytek.easytrip.api.favourite.dto.FavouriteCityDto;
import pl.trytek.easytrip.common.response.JsonResponse;
import pl.trytek.easytrip.common.response.JsonResponseBuilder;

import java.security.Principal;
import java.util.List;

import static pl.trytek.easytrip.common.util.StringUtils.*;
import static pl.trytek.easytrip.common.util.StringUtils.UNAUTHORIZED_ACCESS;

@AllArgsConstructor
@RestController
@RequestMapping(API + "favourite")
public class FavouriteController {

    private final FavouriteService favouriteService;

    private static final String SWAGGER_TAG = "Zarządzanie sekcją ulubione";

    @Operation(summary = "Pobranie listy ulubionych miast", description = "Pobranie listy ulubionych miast",
            tags = {SWAGGER_TAG }, responses = { @ApiResponse(responseCode = "200", description = RESPONSE_OK),
            @ApiResponse(responseCode = "400", description = BAD_REQUEST, content = @Content),
            @ApiResponse(responseCode = "401", description = UNAUTHORIZED_ACCESS, content = @Content),})
    @GetMapping("list")
    public JsonResponse<List<FavouriteCityDto>> getFavourites(Principal principal) {
        return JsonResponseBuilder.ok(favouriteService.getFavourites(principal));
    }

    @Operation(summary = "Pobranie listy ulubionych atrakcji w podanym mieście", description = "Pobranie listy ulubionych atrakcji w podanym mieściet",
            tags = {SWAGGER_TAG }, responses = { @ApiResponse(responseCode = "200", description = RESPONSE_OK),
            @ApiResponse(responseCode = "400", description = BAD_REQUEST, content = @Content),
            @ApiResponse(responseCode = "401", description = UNAUTHORIZED_ACCESS, content = @Content),})
    @GetMapping("city/{city}")
    public JsonResponse<List<FavouriteAttractionDto>> getFavouritesByCity(@PathVariable String city, Principal principal) {
        return JsonResponseBuilder.ok(favouriteService.getFavouritesByCity(city,principal));
    }

    @GetMapping("{favouriteId}")
    public JsonResponse<FavouriteAttractionDto> getFavourite(@PathVariable Long favouriteId) {
        return JsonResponseBuilder.ok(favouriteService.getFavourite(favouriteId));
    }

    @PostMapping("{attractionId}")
    public JsonResponse<Long> addFavourite(@PathVariable Long attractionId, Principal principal, @ParameterObject String note) {
        return JsonResponseBuilder.ok(favouriteService.addFavourite(attractionId, principal, note));
    }

    @PutMapping("{favouriteId}")
    public JsonResponse<Long> editFavouriteAttractionNote(@PathVariable Long favouriteId, @ParameterObject String note) {
        return JsonResponseBuilder.ok(favouriteService.editFavouriteAttractionNote(favouriteId, note));
    }

    @DeleteMapping("{favouriteId}")
    public JsonResponse<Void> deleteFavourite(@PathVariable Long favouriteId, Principal principal) {
        favouriteService.deleteFavourite(favouriteId);
        return JsonResponseBuilder.ok();
    }

    @DeleteMapping("attraction/{attractionId}")
    public JsonResponse<Void> deleteFavouriteByAttractionId(@PathVariable Long attractionId, Principal principal) {
        favouriteService.deleteFavouriteByAttractionId(attractionId, principal);
        return JsonResponseBuilder.ok();
    }
}
