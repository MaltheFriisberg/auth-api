package auth_api.controllers;

import auth_api.database.interfaces.IUserRepository;
import auth_api.models.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final IUserRepository userRepository;

    @Autowired
    public UserController(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Iterable<User> findAllEmployees() {
        return this.userRepository.findAll();
    }

    @PostMapping()
    public User addOneEmployee(@Valid @RequestBody User user) {
        return this.userRepository.save(user);
    }

    @GetMapping("admin")
    public String helloAdmin() {
        return "Hello Admin";
    }
}
