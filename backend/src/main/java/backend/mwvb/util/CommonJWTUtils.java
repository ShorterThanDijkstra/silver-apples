package backend.mwvb.util;

import backend.mwvb.exception.UserRegisterException;
import io.jsonwebtoken.*;
import lombok.val;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public class CommonJWTUtils {
    private static String lastToken;

    public static String create(String subject, String jwtKey, Long expireMillis) {
        JwtBuilder builder = commonBuilder(subject, jwtKey);
        Date expire = new Date(System.currentTimeMillis() + expireMillis);
        builder.setExpiration(expire);

        String jwtToken = builder.compact();

        cacheToken(jwtToken);
        return jwtToken;
    }

    public static String create(String subject, String jwtKey, Long expireMillis, Map<String, Object> claims) {
        JwtBuilder builder = commonBuilder(subject, jwtKey);

        Date expire = new Date(System.currentTimeMillis() + expireMillis);
        builder.setExpiration(expire);

        builder.addClaims(claims);

        String jwtToken = builder.compact();

        cacheToken(jwtToken);
        return jwtToken;
    }

    public static void verifySubject(Claims claims, String expectedSubject) {
        val subject = claims.getSubject();
        if (!subject.equals(expectedSubject)) {
            throw new JwtException("Wrong JWT Subject, expected: " +
                    expectedSubject + ", actual: " + subject);
        }
    }

    public static String create(String subject, String jwtKey, Map<String, Object> claims) {
        JwtBuilder builder = commonBuilder(subject, jwtKey);

        builder.addClaims(claims);

        String jwtToken = builder.compact();

        cacheToken(jwtToken);
        return jwtToken;
    }

    public static String create(String subject, String jwtKey) {
        JwtBuilder builder = commonBuilder(subject, jwtKey);

        String jwtToken = builder.compact();

        cacheToken(jwtToken);

        return jwtToken;
    }

    private static void cacheToken(String jwtToken) {
        lastToken = jwtToken;
    }

    public static String lastToken() {
        return lastToken;
    }

    public static JwtBuilder commonBuilder(String subject, String jwtKey) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = secretKey(jwtKey);
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(subject)
                .setIssuedAt(new Date())
                .signWith(signatureAlgorithm, secretKey);
    }

    private static SecretKey secretKey(String key) {
        byte[] encodedKey = Base64.getDecoder().decode(key);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    public static Claims parse(String jwtToken, String jwtKey) {
        SecretKey secretKey = secretKey(jwtKey);
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    public static String parse(String jwtToken, String jwtKey, String subject, String key, Function<String, RuntimeException> exception) throws UserRegisterException {
        try {
            Claims claims = parse(jwtToken, jwtKey);
            verifySubject(claims, subject);
            return claims.get(key, String.class);
        } catch (JwtException e) {
            throw exception.apply(e.toString());
        }
    }

}
