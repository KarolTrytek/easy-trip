package pl.trytek.easytrip.api.user;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.trytek.easytrip.api.user.dto.UserDto;
import pl.trytek.easytrip.api.user.dto.UserUpdateDto;
import pl.trytek.easytrip.common.response.JsonResponse;
import pl.trytek.easytrip.common.response.JsonResponseBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @GetMapping("{userId}")
    public JsonResponse<UserDto> getUserById(Long id) {
        return JsonResponseBuilder.ok(userService.getUserById(id));
    }

    @PutMapping()
    public JsonResponse<String> updateUser(@RequestBody UserUpdateDto updateUser) {
        return JsonResponseBuilder.ok(userService.updateUser(updateUser));
    }

    @GetMapping("list")
    public JsonResponse<List<UserDto>> getUsers() {
        return JsonResponseBuilder.ok(userService.getUsers());
    }

    @GetMapping("/token/refresh")
    public String refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return userService.refreshToken(request, response);
    }

    @DeleteMapping("{userId}")
    public JsonResponse<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return JsonResponseBuilder.ok();
    }
}
