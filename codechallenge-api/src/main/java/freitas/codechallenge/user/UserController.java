package freitas.codechallenge.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
    public ResponseEntity<UserDto> findById(@PathVariable @Positive @NotNull long id) {
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

    @PatchMapping(path = "/recover-password")
    public ResponseEntity<Void> updatePassword(JwtAuthenticationToken jwt,
                                               @RequestBody @Size(min = 8)
                                               @NotBlank String newPassword) {
        userService.updatePassword(jwt, newPassword);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Positive @NotNull long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
