package ca.ulaval.glo4003.ultaxi.infrastructure.user.jwt;

import ca.ulaval.glo4003.ultaxi.domain.user.TokenManager;
import ca.ulaval.glo4003.ultaxi.infrastructure.user.jwt.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

public class JWTTokenManager implements TokenManager {

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
    private final String secretKey = "equipe5";

    public String createToken(String subject, long ttlInMillis) {

        String uuid = UUID.randomUUID().toString();

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder jwtBuilder = Jwts.builder().setId(uuid).setIssuedAt(now).setSubject(subject)
                .signWith(signatureAlgorithm, signingKey);

        if (ttlInMillis > 0) {
            long expMillis = nowMillis + ttlInMillis;
            Date exp = new Date(expMillis);
            jwtBuilder.setExpiration(exp);
        }

        return jwtBuilder.compact();
    }

    public Claims parseToken(String token) {
        try {
            return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            throw new InvalidTokenException("Token can't be parsed.");
        }
    }

    public void validateTokenExpirationDate(String token) {

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        Date tokenExpiration = parseToken(token).getExpiration();

        if (tokenExpiration.before(now)) {
            throw new InvalidTokenException("Token is expired.");
        }

    }

    public String getUsername(String token) {
        return parseToken(token).getSubject();
    }

    public String getTokenId(String token) {
        return parseToken(token).getId();
    }
}
