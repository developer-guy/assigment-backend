package com.minidolap.persistence.repository.user;

import com.minidolap.dto.ApplicationUserWrapper;
import com.minidolap.persistence.entity.login.ApplicationUser;
import com.minidolap.persistence.entity.login.Role;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;

@Service
@Transactional(propagation = Propagation.REQUIRED)
@RequiredArgsConstructor
public class ApplicationUserDetailsServiceImpl implements ApplicationUserDetailService {
    private final ApplicationUserRepository applicationUserRepository;
    private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
        Hibernate.initialize(applicationUser.getRoles());
        ApplicationUserWrapper applicationUserWrapper = new ApplicationUserWrapper(applicationUser);
        if (Objects.isNull(applicationUser)) {
            throw new UsernameNotFoundException(username + "not found!!!");
        }
        detailsChecker.check(applicationUserWrapper);
        return applicationUserWrapper;
    }
}
