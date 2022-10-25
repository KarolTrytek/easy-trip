package pl.trytek.easytrip.api.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.trytek.easytrip.data.domain.UserRoleEnum;
import pl.trytek.easytrip.data.repository.UserRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
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

        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), true,
                true, true, true, getAuthorities(user.getRole()));
    }

    public Collection<? extends GrantedAuthority> getAuthorities(UserRoleEnum role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.getCode()));
        return authorities;
    }
}
