package pl.trytek.easytrip.api.registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.trytek.easytrip.common.response.JsonResponse;
import pl.trytek.easytrip.common.response.JsonResponseBuilder;

@AllArgsConstructor
@RestController
@RequestMapping("api/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public JsonResponse<String> register(@RequestBody RegistrationDto params) {
        return JsonResponseBuilder.ok(registrationService.register(params));
    }
}
