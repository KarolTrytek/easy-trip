package pl.trytek.easytrip.api.attraction;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import pl.trytek.easytrip.api.attraction.dto.AttractionCriteriaDto;
import pl.trytek.easytrip.api.attraction.dto.AttractionDetailsDto;
import pl.trytek.easytrip.api.attraction.dto.AttractionGridDto;
import pl.trytek.easytrip.api.attraction.dto.CountryDto;
import pl.trytek.easytrip.common.response.JsonResponse;
import pl.trytek.easytrip.common.response.JsonResponseBuilder;

import java.security.Principal;
import java.util.List;

import static pl.trytek.easytrip.common.util.StringUtils.*;

@AllArgsConstructor
@RestController
@CrossOrigin
@RequestMapping(API+"attraction")
public class AttractionController {

    private static final String SWAGGER_TAG = "Zarządzanie atrakcjami";

    private final AttractionService attractionService;

    @Operation(summary = "Lista wszystkich atrakcji", description = "Lista wszystkich atrakcji",
            tags = {SWAGGER_TAG}, responses = {@ApiResponse(responseCode = "200", description = "OK")})
    @CrossOrigin
    @GetMapping("list")
    public JsonResponse<List<AttractionGridDto>> findAttractions() {
        return JsonResponseBuilder.ok(attractionService.getAttractions());
    }

    @Operation(summary = "Lista wszystkich krajów", description = "Lista wszystkich krajów",
            tags = {SWAGGER_TAG}, responses = {@ApiResponse(responseCode = "200", description = "OK")})
    @CrossOrigin
    @GetMapping("country/list")
    public JsonResponse<List<CountryDto>> findCountries() {
        return JsonResponseBuilder.ok(attractionService.getCountries());
    }

    @GetMapping("{attractionId}/details")
    @CrossOrigin
    public JsonResponse<AttractionDetailsDto> getDetails(@PathVariable Long attractionId) {
        return JsonResponseBuilder.ok(attractionService.getAttractionDetails(attractionId));
    }

    @PostMapping("{attractionId}/like")
    @CrossOrigin
    public JsonResponse<Long> likeUnlikeAttraction(@PathVariable Long attractionId, Principal principal) {
        return JsonResponseBuilder.ok(attractionService.likeUnlikeAttraction(attractionId, principal));
    }

    @PostMapping("{attractionId}/favourite")
    public JsonResponse<Long> addAttractionToFavourite(@PathVariable Long attractionId, Principal principal) {
        return JsonResponseBuilder.ok(attractionService.addAttractionToFavourite(attractionId, principal));
    }


    @Operation(summary = "Pobranie listy atrakcji", description = "Pobiera listy atrakcji spełniającej podane kryteria",
            tags = {SWAGGER_TAG }, responses = { @ApiResponse(responseCode = "200", description = RESPONSE_OK),
            @ApiResponse(responseCode = "400", description = BAD_REQUEST, content = @Content),
            @ApiResponse(responseCode = "401", description = UNAUTHORIZED_ACCESS, content = @Content),})
    @CrossOrigin
    @GetMapping("list/grid")
    public JsonResponse<List<AttractionGridDto>> getAttractions(@ParameterObject AttractionCriteriaDto criteria) {
        return JsonResponseBuilder.ok(attractionService.getAttractions(criteria));
    }

    @Operation(summary = "Pobranie listy atrakcji", description = "Pobiera listy atrakcji spełniającej podane kryteria",
            tags = {SWAGGER_TAG }, responses = { @ApiResponse(responseCode = "200", description = RESPONSE_OK),
            @ApiResponse(responseCode = "400", description = BAD_REQUEST, content = @Content),
            @ApiResponse(responseCode = "401", description = UNAUTHORIZED_ACCESS, content = @Content),})
    @CrossOrigin
    @PostMapping()
    public JsonResponse<String> addAttraction(@ParameterObject AttractionCriteriaDto criteria) {
        return JsonResponseBuilder.ok(attractionService.addAttraction(criteria));
    }



}
