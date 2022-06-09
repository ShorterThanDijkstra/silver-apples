package backend.mwvb.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

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
}
