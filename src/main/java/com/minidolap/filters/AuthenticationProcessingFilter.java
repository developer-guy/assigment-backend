package com.minidolap.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minidolap.dto.LoginCredentialsDTO;
import com.minidolap.utility.login.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class AuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
    private final TokenService tokenService;

    public AuthenticationProcessingFilter(String antMatcherUrl,
                                          AuthenticationManager authManager,
                                          TokenService tokenService) {
        super(new AntPathRequestMatcher(antMatcherUrl));
        setAuthenticationManager(authManager);
        this.tokenService = tokenService;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse)
            throws AuthenticationException, IOException {
        LoginCredentialsDTO loginCredentialsDTO = new ObjectMapper().readValue(httpServletRequest.getInputStream(),
                LoginCredentialsDTO.class);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginCredentialsDTO.getUsername(),
                        loginCredentialsDTO.getPassword(),
                        Collections.emptyList());
        return getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) {
        UserDetails userDetails = (UserDetails) authResult.getPrincipal();
        tokenService.createTokenFromUserInformationsAndAddtoHeader(response, userDetails);
    }
}
