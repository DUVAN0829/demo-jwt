package co.duvan.security.app.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
                .expiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getKey())
                .compact();

    }

    private Key getKey() {

        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);

    }

}
