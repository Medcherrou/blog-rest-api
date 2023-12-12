package com.springboot.blog.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.springboot.blog.payload.JwtAuthResponse;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.SignUpDto;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.security.JwtTokenProvider;
import com.springboot.blog.service.AuthService;
import com.springboot.blog.service.impl.AuthServiceImpl;

import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.intercept.RunAsImplAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class AuthControllerTest {



    /**
     * Method under test: {@link AuthController#login(LoginDto)}
     */
    @Test
    void testLogin3() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        AuthServiceImpl authService = mock(AuthServiceImpl.class);
        when(authService.login(Mockito.<LoginDto>any())).thenReturn("Login");
        AuthController authController = new AuthController(authService);

        LoginDto loginDto = new LoginDto();
        loginDto.setPassword("iloveyou");
        loginDto.setUsernameOrEmail("janedoe");
        ResponseEntity<JwtAuthResponse> actualLoginResult = authController.login(loginDto);
        assertTrue(actualLoginResult.hasBody());
        assertTrue(actualLoginResult.getHeaders().isEmpty());
        assertEquals(200, actualLoginResult.getStatusCodeValue());
        JwtAuthResponse body = actualLoginResult.getBody();
        assertEquals("Bearer", body.getTokenType());
        assertEquals("Login", body.getAccessToken());
        verify(authService).login(Mockito.<LoginDto>any());
    }


    /**
     * Method under test: {@link AuthController#register(SignUpDto)}
     */
    @Test
    void testRegister3() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        AuthService authService = mock(AuthService.class);
        when(authService.register(Mockito.<SignUpDto>any())).thenReturn("Register");
        AuthController authController = new AuthController(authService);

        SignUpDto registerDto = new SignUpDto();
        registerDto.setEmail("jane.doe@example.org");
        registerDto.setName("Name");
        registerDto.setPassword("iloveyou");
        registerDto.setUsername("janedoe");
        ResponseEntity<String> actualRegisterResult = authController.register(registerDto);
        assertEquals("Register", actualRegisterResult.getBody());
        assertEquals(201, actualRegisterResult.getStatusCodeValue());
        assertTrue(actualRegisterResult.getHeaders().isEmpty());
        verify(authService).register(Mockito.<SignUpDto>any());
    }
}

