package btc_shop.controllers;

import btc_shop.database.entities.ApplicationUser;
import btc_shop.database.interfaces.IApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationUserController {
    private final IApplicationUserRepository applicationUserRepository;

    @Autowired
    public ApplicationUserController(IApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @GetMapping("/applicationuser")
    public Iterable<ApplicationUser> findAllEmployees() {
        return this.applicationUserRepository.findAll();
    }

    @PostMapping("/applicationuser")
    public ApplicationUser addOneEmployee(@RequestBody ApplicationUser applicationUser) {
        return this.applicationUserRepository.save(applicationUser);
    }

    @GetMapping("admin")
    public String helloAdmin() {
        return "Hello Admin";
    }
}
