package freitas.codechallenge.user;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable @Positive long id) {
        var user = userService.findById(id);

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
}
