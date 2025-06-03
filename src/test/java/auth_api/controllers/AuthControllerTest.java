package auth_api.controllers;

import auth_api.exceptions.EmailAlreadyRegisteredException;
import auth_api.exceptions.UserAlreadyRegisteredException;
import auth_api.request.SignUpRequest;
import auth_api.service.IAuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IAuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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


