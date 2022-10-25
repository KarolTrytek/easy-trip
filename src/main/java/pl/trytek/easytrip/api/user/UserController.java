package pl.trytek.easytrip.api.user;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.trytek.easytrip.common.response.JsonResponse;
import pl.trytek.easytrip.common.response.JsonResponseBuilder;

import java.security.Principal;
import java.util.List;

import static pl.trytek.easytrip.common.util.StringUtils.API;

@AllArgsConstructor
@RestController
@RequestMapping(API + "user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public JsonResponse<UserDto> getLoggedUser(Principal principal) {
        return JsonResponseBuilder.ok(userService.getLoggedUserData(principal));
    }

    @GetMapping("list")
    public JsonResponse<List<UserDto>> getUsers() {
        return JsonResponseBuilder.ok(userService.getUsers());
    }
}
