package com.minidolap.dto;

import com.minidolap.persistence.entity.login.ApplicationUser;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
public class ApplicationUserWrapper implements UserDetails {
    private final ApplicationUser applicationUser;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return applicationUser.getRoles().
                stream().
                map(role -> new SimpleGrantedAuthority(role.getRoleName())).
                collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public String getPassword() {
        return applicationUser.getPassword();
    }

    @Override
    public String getUsername() {
        return applicationUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return applicationUser.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return applicationUser.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return applicationUser.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return applicationUser.isEnabled();
    }
}
