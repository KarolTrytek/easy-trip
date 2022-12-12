package pl.trytek.easytrip.api.registration;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.trytek.easytrip.common.exception.EasyTripApiException;
import pl.trytek.easytrip.common.exception.ExceptionCode;
import pl.trytek.easytrip.data.domain.User;
import pl.trytek.easytrip.data.domain.UserRoleEnum;
import pl.trytek.easytrip.data.repository.UserRepository;

import java.time.LocalDate;

@Slf4j
@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String register(RegistrationDto params) {

        var userFromDb = userRepository.findByLogin(params.username());

        if (userFromDb.isPresent()) {
            throw new EasyTripApiException(ExceptionCode.UNPROCESSABLE, "Uzytkownik o tej samej nazwie jest już w systemie");
        } else {
            String encodedPassword = bCryptPasswordEncoder.encode(params.password());

            var newUser = new User(params.username(), encodedPassword, UserRoleEnum.USER, LocalDate.now());
            return userRepository.save(newUser).getLogin();
        }
    }
}

