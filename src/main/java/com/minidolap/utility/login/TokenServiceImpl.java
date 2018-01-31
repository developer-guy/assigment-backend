package com.minidolap.utility.login;

import com.minidolap.persistence.repository.user.ApplicationUserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

import static com.minidolap.utility.login.TokenConstants.*;

@Data
public class TokenServiceImpl implements TokenService {
    @Value(value = "${token.secretKey}")
    private String secretKey;

    private final ApplicationUserRepository applicationUserRepository;

    @Override
    public void createTokenFromUserInformationsAndAddtoHeader(HttpServletResponse response, UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userDetails.getUsername());
        claims.put("authorities", userDetails.getAuthorities());
        String jwt = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();


        response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + jwt);
    }

    @Override
    public Authentication getUserDetailsFromToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HEADER_STRING);

        if (StringUtils.isEmpty(authorizationHeader)) {
            return null;
        }

        String token = authorizationHeader.replace(TOKEN_PREFIX, "").trim();

        Claims body = Jwts.parser().
                setSigningKey(secretKey).
                parseClaimsJws(token).
                getBody();

        if (isTokenExpire(body)) {
            return null;
        }

        String userName = (String) body.get("username");
        List<LinkedHashMap<String, String>> authoritiesMapList = (List<LinkedHashMap<String, String>>) body.get("authorities");
        Collection<SimpleGrantedAuthority> authorities = authoritiesMapList.
                stream().
                map(linkedHashMap -> new SimpleGrantedAuthority(linkedHashMap.get("authority"))).
                collect(Collectors.toCollection(ArrayList::new));

        return new UsernamePasswordAuthenticationToken(userName,
                null,
                authorities);
    }

    @Override
    public boolean isTokenExpire(Claims body) {
        boolean isExpire = false;
        Date now = new Date();
        Date expiration = body.getExpiration();
        if (now.after(expiration)) {
            isExpire = true;
        }
        return isExpire;
    }
}
