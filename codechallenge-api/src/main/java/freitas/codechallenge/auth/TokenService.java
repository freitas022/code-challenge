package freitas.codechallenge.auth;

import freitas.codechallenge.role.Role;
import freitas.codechallenge.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtEncoder jwtEncoder;

    public TokenResponseDto generateToken(User user) {
        var claims = buildJwtClaims(user);
        var accessToken = encodeJwtToken(claims);
        return new TokenResponseDto(accessToken);
    }

    private JwtClaimsSet buildJwtClaims(User user) {
        Instant now = Instant.now();

        String scopes = getScopes(user);

        return JwtClaimsSet.builder()
                .issuer("github.com/freitas022")
                .subject(user.getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(2700L)) // 2700s = 45 MIN
                .claim("scope", scopes)
                .build();
    }

    private static String getScopes(User user) {
        return user.getRoles()
                .stream()
                .map(Role::getAuthority)
                .collect(Collectors.joining(" "));
    }

    private String encodeJwtToken(JwtClaimsSet claims) {
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

}
