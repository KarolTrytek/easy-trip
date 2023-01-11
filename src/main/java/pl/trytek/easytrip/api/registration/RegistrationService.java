package pl.trytek.easytrip.api.registration;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.trytek.easytrip.common.exception.EasyTripApiException;
import pl.trytek.easytrip.common.exception.ExceptionCode;
import pl.trytek.easytrip.data.domain.User;
import pl.trytek.easytrip.data.domain.UserRole;
import pl.trytek.easytrip.data.repository.RoleRepository;
import pl.trytek.easytrip.data.repository.UserRepository;
import pl.trytek.easytrip.data.repository.UserRoleRepository;

import java.time.LocalDate;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
@CrossOrigin
public class RegistrationService {

    private final static String USER = "user";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String register(RegistrationDto params) {

        var userFromDb = userRepository.findByLogin(params.username());

        if (userFromDb.isPresent()) {
            throw new EasyTripApiException(ExceptionCode.UNPROCESSABLE, "Użytkownik o tej samej nazwie jest już w systemie");
        } else {
            String encodedPassword = bCryptPasswordEncoder.encode(params.password());

            var roleFromDb = roleRepository.findByName(USER);


            var newUser = new User(params.username(), encodedPassword, LocalDate.now());

           // var newUserRole = new UserRole(newUser, roleFromDb);


          // var newRole =  addRoleToUser(newUser, USER);

           // userRepository.save(newUser);

            var newUserRole = new UserRole( newUser, roleFromDb);

            //userRoleRepository.save(newUserRole);

            newUser.setRoles(Set.of(newUserRole));

            userRepository.save(newUser);



            return newUser.getLogin();
        }
    }

//    private UserRole addRoleToUser(User user, String roleName) {
//        var roles = user.getRoles();
//        var roleFromDb = roleRepository.findByName(roleName);
//        var newUserRole = new UserRole(user, roleFromDb);
//
//        roles.add(newUserRole);
//        user.setRoles(roles);
//        //userRepository.save(user);
//
//        return newUserRole;
//
//       // userRoleRepository.save(newUserRole);
//
//    }
}

