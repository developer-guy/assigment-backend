package com.minidolap.rest;

import com.minidolap.persistence.entity.login.ApplicationUser;
import com.minidolap.persistence.repository.user.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {
    private final ApplicationUserRepository applicationUserRepository;


    @GetMapping
    public List<ApplicationUser> all() {
        return applicationUserRepository.findAll();
    }
}
