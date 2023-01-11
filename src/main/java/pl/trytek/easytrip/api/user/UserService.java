package pl.trytek.easytrip.api.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.trytek.easytrip.api.user.dto.UserDto;
import pl.trytek.easytrip.api.user.dto.UserUpdateDto;
import pl.trytek.easytrip.common.exception.EasyTripApiException;
import pl.trytek.easytrip.common.exception.ExceptionCode;
import pl.trytek.easytrip.data.domain.Role;
import pl.trytek.easytrip.data.domain.User;
import pl.trytek.easytrip.data.domain.UserRole;
import pl.trytek.easytrip.data.repository.RoleRepository;
import pl.trytek.easytrip.data.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static pl.trytek.easytrip.common.util.StringUtils.ROLES;

@Slf4j
@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    public UserDto getLoggedUserData(Principal principal) {
        return userMapper.map(userRepository.findOneByLogin(principal.getName()));
    }

    public List<UserDto> getUsers() {
        return userMapper.map(userRepository.findAll());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Brak w systemie uzytkownika o nazwie %s", username)));

        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), getAuthorities(user.getRoles()));
    }

    public Collection<? extends GrantedAuthority> getAuthorities(Collection<UserRole> roles) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRole().getName()));
        });
        return authorities;
    }

    public UserDto getUserById(Long id) {
        return null;
    }

    @Transactional
    public String updateUser(UserUpdateDto updateUser) {
        var userToUpdate = userRepository.findById(updateUser.userId()).orElseThrow(() -> new EasyTripApiException(ExceptionCode.VALIDATION_ERR, "Brak uzytkownika o podanym id"));

        var newRoles = roleRepository.findAllByNameIn(updateUser.roles());

        var oldRoles = userToUpdate.getRoles().stream().map(UserRole::getRole).toList();
        var oldUserRoles = userToUpdate.getRoles();

        for (Role newRole : newRoles) {
            if (!oldRoles.contains(newRole)) {
                addRoleToUser(userToUpdate, newRole, oldUserRoles);
            }
        }

        userToUpdate.getRoles().removeIf(oldUserRole -> !newRoles.contains(oldUserRole.getRole()));

        userRepository.save(userToUpdate);

        return userToUpdate.getLogin();
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    private void addRoleToUser(User user, Role role, Set<UserRole> roles) {
        roles.add(new UserRole(user, role));
    }

    public String refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String tokens = "";

        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secretEasyTrip".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();

                User user = userRepository.findOneByLogin(username);
                var userRoles = user.getRoles().stream().map(role->role.getRole().getName()).toList();

                String access_token = JWT.create()
                        .withSubject(user.getLogin())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                        .withClaim(ROLES, userRoles)
                        .sign(algorithm);

                response.setHeader("access_token", access_token);
                response.setHeader("refresh_token", refresh_token);
                //response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
//                response.getWriter().write("{\"access_token\":" + "\"" + access_token + "\"," +
//                        " \"refresh_token\":" + "\"" + refresh_token + "\"," +
//                        " \"roles\":" + "\"" + userRoles + "\"," +
//                        " \"status\":" + "\"" + "200" + "\"," +
//                        " \"user_name\":" + "\"" + user.getLogin() + "\"}");

                JSONObject jsonTokens = new JSONObject();
                jsonTokens.put("access_token", access_token);
                jsonTokens.put("refresh_token", refresh_token);
                jsonTokens.put("status", "200");
                jsonTokens.put("roles", userRoles);
                jsonTokens.put("user_name", user.getLogin());

//                JSONArray roles = new JSONArray();
//                JSONObject role = new JSONObject();
//                role.put("refresh_token", refresh_token);

                tokens = jsonTokens.toString();
//                        String.format(
//                        "{\"access_token\":\"%s\"," +
//                        " \"refresh_token\":\"%s\"" +
//                        " \"roles\":" + "\"" + userRoles + "\"," +
//                        " \"status\":" + "\"" + "200" + "\"," +
//                        " \"user_name\":" + "\"" + user.getLogin() + "\"}", access_token, refresh_token);

            } catch (Exception e) {
                log.error("Error logging in: {}", e.getMessage());
                response.setHeader("error", e.getMessage());
                response.sendError(FORBIDDEN.value(), e.getMessage());
            }
        } else {
            throw new EasyTripApiException(ExceptionCode.VALIDATION_ERR, "Brak tokena odświeżającego");
        }

        return tokens;
    }


}


