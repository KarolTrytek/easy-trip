package pl.trytek.easytrip.api.attraction;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.trytek.easytrip.api.attraction.dto.AttractionDetailsDto;
import pl.trytek.easytrip.api.attraction.dto.AttractionDto;
import pl.trytek.easytrip.common.response.JsonResponse;
import pl.trytek.easytrip.common.response.JsonResponseBuilder;

import java.util.List;

import static pl.trytek.easytrip.common.util.StringUtils.API;

@AllArgsConstructor
@RestController
@RequestMapping(API+"attraction")
public class AttractionController {

    private final AttractionService attractionService;

    @GetMapping("list")
    public JsonResponse<List<AttractionDto>> findAttractions() {
        return JsonResponseBuilder.ok(attractionService.getAttractions());
    }

    @GetMapping("{attractionId}/details")
    public JsonResponse<AttractionDetailsDto> getDetails(@PathVariable Long attractionId) {
        return JsonResponseBuilder.ok(attractionService.getAttractionDetails(attractionId));
    }
}
