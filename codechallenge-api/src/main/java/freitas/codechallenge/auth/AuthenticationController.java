package freitas.codechallenge.auth;

import freitas.codechallenge.user.UserDto;
import freitas.codechallenge.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping(path = "/sign-in")
    public ResponseEntity<TokenResponseDto> signIn(@RequestBody @Valid AuthRequestDto dto) {
        var accessToken = authenticationService.signIn(dto);
        return ResponseEntity.ok().body(accessToken);
    }

    @PostMapping(path = "/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody @Valid UserDto userDto) {
        userService.insert(userDto);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(userDto.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
