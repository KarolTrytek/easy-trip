package pl.trytek.easytrip.api.attraction;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.trytek.easytrip.common.response.JsonResponse;
import pl.trytek.easytrip.common.response.JsonResponseBuilder;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("attraction")
public class AttractionController {

	private final AttractionService attractionService;

	@GetMapping("list")
	public JsonResponse<List<AttractionDto>> findAttractions() {
		return JsonResponseBuilder.ok(attractionService.getAttractions());
	}
}
