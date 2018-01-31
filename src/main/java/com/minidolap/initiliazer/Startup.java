package com.minidolap.initiliazer;

import com.minidolap.persistence.entity.login.ApplicationUser;
import com.minidolap.persistence.entity.login.Role;
import com.minidolap.persistence.repository.user.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;

@RequiredArgsConstructor
public class Startup implements CommandLineRunner {
    private final ApplicationUserRepository applicationUserRepository;

    @Override
    public void run(String... strings) throws Exception {
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setAccountNonExpired(true);
        applicationUser.setAccountNonLocked(true);
        applicationUser.setCredentialsNonExpired(true);
        applicationUser.setUsername("bapaydin67");
        applicationUser.setPassword("12345");
        applicationUser.setEnabled(true);
        Role role = new Role();
        role.setRoleName("ROLE_CUSTOMER");

        applicationUser.getRoles().add(role);


        role.getApplicationUser().add(applicationUser);
        applicationUserRepository.save(applicationUser);
    }
}
