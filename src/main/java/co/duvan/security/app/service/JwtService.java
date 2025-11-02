package co.duvan.security.app.service;

import co.duvan.security.app.model.User;
import io.jsonwebtoken.Claims;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    //* Vars
    @Value("${jwt.key}")
    private String SECRET_KEY;

    //* Methods
    public String getToken(User user) {

        return getToken(new HashMap<>(), user);

    }

    //* Methods
    //* Crear token
    private String getToken(Map<String, Object> extraClaims, User user) {

        return Jwts.builder()
                .claims(extraClaims)
                .claim("userId", user.getId())
                .claim("firstName", user.getFirstName())
                .claim("lastname", user.getLastName())
                .claim("role", user.getRole())
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

    public boolean isTokenValid(String token, User user) {

        final String username = getUsernameFromToken(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token)); //* El token se valida si el user es el mismo del token y de la DB

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
