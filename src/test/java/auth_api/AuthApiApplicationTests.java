package auth_api;

import auth_api.controllers.AuthController;
import auth_api.controllers.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import static org.assertj.core.api.Assertions.assertThat;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class AuthApiApplicationTests {

	@Autowired
	private AuthController authController;

	@Autowired
	private UserController userController;

	@Test
	void contextLoads() {
		assertThat(authController).isNotNull();
		assertThat(userController).isNotNull();
	}

}
