package freitas.codechallenge.auth;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AuthRequestDto(
        @NotEmpty @NotNull String email,
        @NotEmpty @NotNull String password
) {
}
