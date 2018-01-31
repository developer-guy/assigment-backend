package com.minidolap.login;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.minidolap.MinidolapCoreApplication;
import com.minidolap.configuration.datasource.H2JpaTestConfiguration;
import com.minidolap.dto.LoginCredentialsDTO;
import com.minidolap.persistence.entity.login.ApplicationUser;
import com.minidolap.persistence.entity.login.Role;
import com.minidolap.persistence.repository.user.ApplicationUserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@AutoConfigureMockMvc(secure = false)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {MinidolapCoreApplication.class, H2JpaTestConfiguration.class})
@RunWith(SpringRunner.class)
public class LoginControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private ObjectMapper mapper;


    @Test
    public void should_return_401_status_when_send_request_to_secure_endpoint_without_authenticate() throws Exception {
        //GIVEN
        given(applicationUserRepository.findByUsername(any())).
                willReturn(new ApplicationUser());
        //WHEN
        mvc.perform(MockMvcRequestBuilders.get("/users")).
                andExpect(MockMvcResultMatchers.status().is4xxClientError());
        //THEN
        verify(applicationUserRepository, times(0)).findByUsername(any());
    }

    @Test
    public void should_return_200_status_when_send_request_to_secure_endpoint_with_authenticate() throws Exception {
        //GIVEN
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUsername("bapaydin67");
        Role role = new Role(null, "ROLE_CUSTOMER", Collections.singleton(applicationUser));
        applicationUser.setRoles(Collections.singleton(role));
        applicationUser.setCredentialsNonExpired(true);
        applicationUser.setAccountNonExpired(true);
        applicationUser.setEnabled(true);
        applicationUser.setAccountNonLocked(true);
        given(applicationUserRepository.findByUsername(any())).willReturn(applicationUser);
        LoginCredentialsDTO credentials = new LoginCredentialsDTO("bapaydin67", "12345");
        String body = mapper.writeValueAsString(credentials);
        //WHEN
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.post("/login").
                contentType(MediaType.APPLICATION_JSON).content(body)).
                andReturn().getResponse();
        //THEN
        assertThat(response.getHeaderNames(), hasItem("Authorization"));
    }
}
