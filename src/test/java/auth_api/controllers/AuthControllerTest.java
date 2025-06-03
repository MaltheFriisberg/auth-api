package auth_api.controllers;

import auth_api.exceptions.EmailAlreadyRegisteredException;
import auth_api.exceptions.UserAlreadyRegisteredException;
import auth_api.request.LoginRequest;
import auth_api.request.SignUpRequest;
import auth_api.security.services.UserDetailsImpl;
import auth_api.service.IAuthService;
import auth_api.service.SignInResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false) //add filters false means auth is bypassed
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IAuthService authService;

    private final String loginRequest = """
            {
              "username": "existingUser",
              "password": "password123"
            }
            """;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testThatItCanHandleAuthentication() throws Exception {
        UserDetailsImpl mockUserDetails = Mockito.mock(UserDetailsImpl.class);
        Mockito.when(mockUserDetails.getUsername()).thenReturn("testUser");

        SignInResult mockSignInResult = new SignInResult(
                "mock-jwt-token",
                mockUserDetails,
                List.of("ROLE_USER", "ROLE_ADMIN")
        );

        Mockito.when(authService.authenticateUser(Mockito.any(LoginRequest.class)))
                .thenReturn(mockSignInResult);

        mockMvc.perform(post("/api/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequest))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"token\":\"mock-jwt-token\",\"id\":0,\"username\":\"testUser\",\"email\":null,\"roles\":[\"ROLE_USER\",\"ROLE_ADMIN\"]}"));
    }

    @Test
    public void testThatExistingEmailIsHandled() throws Exception {
        testThatExceptionIsHandled(testThatExceptionIsHandled(new UserAlreadyRegisteredException("Error: Username is already taken!")));
    }

    @Test
    public void testThatExistingUserNameIsHandled() throws Exception {
        testThatExceptionIsHandled(new UserAlreadyRegisteredException("Error: Username is already taken!"));

    }

    private Exception testThatExceptionIsHandled(Exception e) throws Exception {
        Mockito.doThrow(e).when(authService).registerUser(Mockito.any(SignUpRequest.class));

        String jsonRequest = """
            {
              "username": "existingUser",
              "email": "existing@example.com",
              "password": "password123"
            }
            """;

        // Act & Assert
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"message\":\"Error: Username is already taken!\"}"));
        return e;
    }
}


