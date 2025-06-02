package auth_api;

import org.springframework.boot.SpringApplication;

public class TestBtcShopApplication {

	public static void main(String[] args) {
		SpringApplication.from(BtcShopApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
