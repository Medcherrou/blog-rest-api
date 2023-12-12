package com.springboot.blog.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.SignUpDto;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AuthServiceImpl.class})
@ExtendWith(SpringExtension.class)
class AuthServiceImplTest {
    @Autowired
    private AuthServiceImpl authServiceImpl;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private UserRepository userRepository;

    /**
     * Method under test: {@link AuthServiceImpl#login(LoginDto)}
     */
    @Test
    void testLogin() throws AuthenticationException {
        when(jwtTokenProvider.generateToken(Mockito.<Authentication>any())).thenReturn("ABC123");
        when(authenticationManager.authenticate(Mockito.<Authentication>any()))
            .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));

        LoginDto loginDto = new LoginDto();
        loginDto.setPassword("iloveyou");
        loginDto.setUsernameOrEmail("janedoe");
        assertEquals("ABC123", authServiceImpl.login(loginDto));
        verify(jwtTokenProvider).generateToken(Mockito.<Authentication>any());
        verify(authenticationManager).authenticate(Mockito.<Authentication>any());
    }

    /**
     * Method under test: {@link AuthServiceImpl#login(LoginDto)}
     */
    @Test
    void testLogin2() throws AuthenticationException {
        when(authenticationManager.authenticate(Mockito.<Authentication>any()))
            .thenThrow(new BlogAPIException(HttpStatus.CONTINUE, "An error occurred"));

        LoginDto loginDto = new LoginDto();
        loginDto.setPassword("iloveyou");
        loginDto.setUsernameOrEmail("janedoe");
        assertThrows(BlogAPIException.class, () -> authServiceImpl.login(loginDto));
        verify(authenticationManager).authenticate(Mockito.<Authentication>any());
    }

    /**
     * Method under test: {@link AuthServiceImpl#register(SignUpDto)}
     */
    @Test
    void testRegister() {
        when(userRepository.existsByUsername(Mockito.<String>any())).thenReturn(true);

        SignUpDto registerDto = new SignUpDto();
        registerDto.setEmail("jane.doe@example.org");
        registerDto.setName("Name");
        registerDto.setPassword("iloveyou");
        registerDto.setUsername("janedoe");
        assertThrows(BlogAPIException.class, () -> authServiceImpl.register(registerDto));
        verify(userRepository).existsByUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link AuthServiceImpl#register(SignUpDto)}
     */
    @Test
    void testRegister2() {
        when(userRepository.existsByEmail(Mockito.<String>any())).thenReturn(true);
        when(userRepository.existsByUsername(Mockito.<String>any())).thenReturn(false);

        SignUpDto registerDto = new SignUpDto();
        registerDto.setEmail("jane.doe@example.org");
        registerDto.setName("Name");
        registerDto.setPassword("iloveyou");
        registerDto.setUsername("janedoe");
        assertThrows(BlogAPIException.class, () -> authServiceImpl.register(registerDto));
        verify(userRepository).existsByEmail(Mockito.<String>any());
        verify(userRepository).existsByUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link AuthServiceImpl#register(SignUpDto)}
     */
    @Test
    void testRegister3() {
        when(userRepository.existsByUsername(Mockito.<String>any()))
            .thenThrow(new BlogAPIException(HttpStatus.CONTINUE, "An error occurred"));

        SignUpDto registerDto = new SignUpDto();
        registerDto.setEmail("jane.doe@example.org");
        registerDto.setName("Name");
        registerDto.setPassword("iloveyou");
        registerDto.setUsername("janedoe");
        assertThrows(BlogAPIException.class, () -> authServiceImpl.register(registerDto));
        verify(userRepository).existsByUsername(Mockito.<String>any());
    }
}

