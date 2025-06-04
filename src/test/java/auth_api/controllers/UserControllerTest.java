package auth_api.controllers;

import auth_api.database.interfaces.IUserRepository;
import auth_api.service.IAuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false) //add filters false means auth is bypassed
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IUserRepository userRepository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testThatItValidatesOnThePostMapping() throws Exception {
        final String signinRequest = """
            {
              "usernameasd": "existingUser",
              "passwordasd": "password123",
              "email": null
            }
            """;

        mockMvc.perform(post("/api/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signinRequest))
                .andExpect(status().isBadRequest());
    }
}
