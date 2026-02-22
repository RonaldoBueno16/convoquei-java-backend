package br.com.convoquei.backend._shared.config.jwt.service;

import br.com.convoquei.backend._shared.config.jwt.JwtProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class JwtService {

    private final JwtProperties props;
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public JwtService(JwtProperties props, JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
        this.props = props;
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    public String generateAccessToken(UUID userId, UserDetails principal) {
        Instant now = Instant.now();

        List<String> roles = principal.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(props.getIssuer())
                .subject(principal.getUsername()) // email
                .issuedAt(now)
                .expiresAt(now.plus(props.getAccessTokenMinutes(), ChronoUnit.MINUTES))
                .claim("uid", userId.toString())
                .claim("typ", "access")
                .claim("roles", roles)
                .build();

        // HS256
        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }

    public String generateRefreshToken(UUID userId, String subjectEmail) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(props.getIssuer())
                .subject(subjectEmail)
                .issuedAt(now)
                .expiresAt(now.plus(props.getRefreshTokenDays(), ChronoUnit.DAYS))
                .claim("uid", userId.toString())
                .claim("typ", "refresh")
                .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }

    public Jwt decode(String token) {
        return jwtDecoder.decode(stripBearer(token));
    }

    public boolean isValid(String token) {
        try {
            jwtDecoder.decode(stripBearer(token));
            return true;
        } catch (JwtException ex) {
            return false;
        }
    }

    public String getSubject(String token) {
        return decode(token).getSubject();
    }

    public UUID getUserId(String token) {
        String uid = decode(token).getClaimAsString("uid");
        return uid == null ? null : UUID.fromString(uid);
    }

    public List<String> getRoles(String token) {
        List<String> roles = decode(token).getClaimAsStringList("roles");
        return roles == null ? List.of() : roles;
    }

    private String stripBearer(String token) {
        if (token == null) return "";
        return token.startsWith("Bearer ") ? token.substring(7) : token;
    }
}