package auth_api.service;

import auth_api.exceptions.EmailAlreadyRegisteredException;
import auth_api.exceptions.UserAlreadyRegisteredException;
import auth_api.models.ERole;
import auth_api.models.Role;
import auth_api.repository.RoleRepository;
import auth_api.repository.UserRepository;
import auth_api.request.LoginRequest;
import auth_api.request.SignUpRequest;
import auth_api.security.jwt.JwtUtils;
import auth_api.security.services.UserDetailsImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals; // JUnit 5
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    JwtUtils jwtUtils;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    AuthService authService;

    @Test
    void testRegisterUser_UsernameTaken_ThrowsException() {
        SignUpRequest request = new SignUpRequest();
        request.setUsername("takenUser");
        request.setEmail("email@example.com");
        request.setPassword("password");

        when(userRepository.existsByUsername("takenUser")).thenReturn(true);

        assertThrows(UserAlreadyRegisteredException.class, () -> {
            authService.registerUser(request);
        });

        verify(userRepository, times(1)).existsByUsername("takenUser");
        verify(userRepository, never()).save(any());
    }

    @Test
    void testRegisterUser_EmailTaken_ThrowsException() {
        SignUpRequest request = new SignUpRequest();
        request.setUsername("newUser");
        request.setEmail("existing@example.com");
        request.setPassword("password");

        when(userRepository.existsByUsername("newUser")).thenReturn(false);
        when(userRepository.existsByEmail("existing@example.com")).thenReturn(true);

        assertThrows(EmailAlreadyRegisteredException.class, () -> {
            authService.registerUser(request);
        });

        verify(userRepository, times(1)).existsByEmail("existing@example.com");
        verify(userRepository, never()).save(any());
    }

    @Test
    void testRegisterUser_WithRole_Success() throws EmailAlreadyRegisteredException, UserAlreadyRegisteredException {
        SignUpRequest request = new SignUpRequest();
        request.setUsername("newUser");
        request.setEmail("new@example.com");
        request.setPassword("password");
        request.setRole(Set.of("admin"));

        Role adminRole = new Role(ERole.ROLE_ADMIN);

        when(userRepository.existsByUsername("newUser")).thenReturn(false);
        when(userRepository.existsByEmail("new@example.com")).thenReturn(false);
        when(roleRepository.findByName(ERole.ROLE_ADMIN)).thenReturn(Optional.of(adminRole));
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        authService.registerUser(request);

        verify(userRepository).save(argThat(user ->
                user.getUsername().equals("newUser") &&
                        user.getPassword().equals("encodedPassword") &&
                        user.getRoles().contains(adminRole)
        ));
    }

    @Test
    void testRegisterUser_NoRoleProvided_AssignsDefaultRole() throws EmailAlreadyRegisteredException, UserAlreadyRegisteredException {
        SignUpRequest request = new SignUpRequest();
        request.setUsername("user");
        request.setEmail("user@example.com");
        request.setPassword("password");
        request.setRole(null);

        Role defaultRole = new Role(ERole.ROLE_USER);

        when(userRepository.existsByUsername("user")).thenReturn(false);
        when(userRepository.existsByEmail("user@example.com")).thenReturn(false);
        when(roleRepository.findByName(ERole.ROLE_USER)).thenReturn(Optional.of(defaultRole));
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        authService.registerUser(request);

        verify(userRepository).save(argThat(user ->
                user.getRoles().contains(defaultRole)
        ));
    }

    /*@Test
    void testAuthenticateUser_Success() {
        LoginRequest request = new LoginRequest();
        request.setUsername("testuser");
        request.setPassword("password");

        Authentication auth = mock(Authentication.class);
        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);

        when(authenticationManager.authenticate(any())).thenReturn(auth);
        when(auth.getPrincipal()).thenReturn(userDetails);
        when(jwtUtils.generateJwtToken(auth)).thenReturn("mocked-jwt");
        when(userDetails.getAuthorities()).thenReturn(
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );

        SignInResult result = authService.authenticateUser(request);

        assertEquals("mocked-jwt", result.jwt());
        assertEquals(List.of("ROLE_USER"), result.roles());
        verify(authenticationManager, times(1)).authenticate(any());
    }*/
}
