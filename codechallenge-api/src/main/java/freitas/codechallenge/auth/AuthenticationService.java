package freitas.codechallenge.auth;

import freitas.codechallenge.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final UserRepository userRepository;

    public TokenResponseDto signIn(AuthRequestDto dto) {
        var userOptional = userRepository.findByEmail(dto.email());

        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        var user = userOptional.get();
        isSamePassword(user.getPassword(), dto.password());

        log.info("Hi, {}", user.getName());

        return tokenService.generateToken(user);
    }

    private void isSamePassword(String hashedPassword, String rawPassword) {
        if (!passwordEncoder.matches(rawPassword, hashedPassword)) {
            throw new BadCredentialsException("Email or password incorrect.");
        }
    }

}
