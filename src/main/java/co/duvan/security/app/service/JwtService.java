package co.duvan.security.app.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    //* Vars
    private final static String SECRET_KEY = "CZc3D9WKy7CdKnonwTWowwXL0587hnkUDnIRQlxYN1M=";

    //* Methods
    public String getToken(UserDetails user) {

        return getToken(new HashMap<>(), user);

    }

    //* Methods
    private String getToken(Map<String, Object> extraClaims, UserDetails user) {

        return Jwts.builder()
                .claims(extraClaims)
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getKey())
                .compact();

    }

    //* Logica final, validar token
    private SecretKey getKey() {

        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);

    }

    public String getUsernameFromToken(String token) {

        return getClaim(token, Claims::getSubject);

    }

    public boolean isTokenValid(String token, UserDetails user) {

        final String username = getUsernameFromToken(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));

    }

    private Claims getAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    private <T> T getClaim(String token, Function<Claims, T> claimsResolver) {

        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);

    }

    private Date getExpirationDate(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return getExpirationDate(token).before(new Date());
    }

}
