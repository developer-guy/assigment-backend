package com.minidolap.utility.login;


import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface TokenService {
    void createTokenFromUserInformationsAndAddtoHeader(HttpServletResponse response, UserDetails userDetails);

    Authentication getUserDetailsFromToken(HttpServletRequest request);

    boolean isTokenExpire(Claims body);
}
