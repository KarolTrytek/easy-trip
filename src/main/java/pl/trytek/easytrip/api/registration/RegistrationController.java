package pl.trytek.easytrip.api.registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.trytek.easytrip.common.response.JsonResponse;
import pl.trytek.easytrip.common.response.JsonResponseBuilder;

import static pl.trytek.easytrip.common.util.StringUtils.API;

@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping(API + "registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public JsonResponse<String> register(@RequestBody RegistrationDto params) {
        return JsonResponseBuilder.ok(registrationService.register(params));
    }
}
