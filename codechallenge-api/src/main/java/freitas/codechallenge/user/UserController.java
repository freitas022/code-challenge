package freitas.codechallenge.user;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable @Positive long id) {
        var user = userService.findById(id);

        return ResponseEntity.ok().body(new UserDto(user));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDto> myProfile(JwtAuthenticationToken jwt) {
        var user = userService.myProfile(jwt);

        return ResponseEntity.ok().body(new UserDto(user));
    }

    @GetMapping
    public ResponseEntity<List<UserMinDto>> findAll() {
        var result = userService.findAll();

        return ResponseEntity.ok().body(
                result.parallelStream()
                        .map(UserMinDto::new)
                        .toList()
        );
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Positive long id, JwtAuthenticationToken jwt) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
