package pl.trytek.easytrip.api.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

import static pl.trytek.easytrip.common.util.StringUtils.*;
import static pl.trytek.easytrip.common.util.StringUtils.UNAUTHORIZED_ACCESS;

@AllArgsConstructor
@RestController
@RequestMapping(API + "user")
public class UserController {

    private static final String SWAGGER_TAG = "Zarządzanie użytkownikami";

    private final UserService userService;

    @GetMapping
    public JsonResponse<UserDto> getLoggedUser(Principal principal) {
        return JsonResponseBuilder.ok(userService.getLoggedUserData(principal));
    }

    @PutMapping()
    public JsonResponse<String> updateUser(@RequestBody UserUpdateDto updateUser) {
        return JsonResponseBuilder.ok(userService.updateUser(updateUser));
    }

    @Operation(summary = "Pobranie listy użytkowników", description = "Pobiera listy użytkowników",
            tags = {SWAGGER_TAG }, responses = { @ApiResponse(responseCode = "200", description = RESPONSE_OK),
            @ApiResponse(responseCode = "400", description = BAD_REQUEST, content = @Content),
            @ApiResponse(responseCode = "401", description = UNAUTHORIZED_ACCESS, content = @Content),})
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
