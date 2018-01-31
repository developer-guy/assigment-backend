package com.minidolap.configuration.bean;


import com.minidolap.initiliazer.Startup;
import com.minidolap.persistence.repository.login.LoginAuthenticationProvider;
import com.minidolap.persistence.repository.user.ApplicationUserRepository;
import com.minidolap.utility.login.TokenService;
import com.minidolap.utility.login.TokenServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public TokenService tokenService(ApplicationUserRepository applicationUserRepository) {
        return new TokenServiceImpl(applicationUserRepository);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationUserRepository applicationUserRepository) {
        return new Startup(applicationUserRepository);
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        return new LoginAuthenticationProvider(userDetailsService);
    }
}
