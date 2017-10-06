package ca.ulaval.glo4003.ultaxi.domain.user;

import io.jsonwebtoken.Claims;

public interface TokenManager {

    String createToken(String subject, long ttlInMillis);

    Claims parseToken(String token);

    String getTokenId(String token);

    void validateTokenExpirationDate(String token);

    String getUsername(String token);
}
