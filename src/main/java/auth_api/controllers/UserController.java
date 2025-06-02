package auth_api.controllers;

import auth_api.database.interfaces.IUserRepository;
import auth_api.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final IUserRepository applicationUserRepository;

    @Autowired
    public UserController(IUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @GetMapping("/applicationuser")
    public Iterable<User> findAllEmployees() {
        return this.applicationUserRepository.findAll();
    }

    @PostMapping("/applicationuser")
    public User addOneEmployee(@RequestBody User applicationUser) {
        return this.applicationUserRepository.save(applicationUser);
    }

    @GetMapping("admin")
    public String helloAdmin() {
        return "Hello Admin";
    }
}
