package btc_shop.controllers;

import btc_shop.database.entities.AppUser;
import btc_shop.database.interfaces.IAppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppUserController {
    private final IAppUserRepository applicationUserRepository;

    @Autowired
    public AppUserController(IAppUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @GetMapping("/applicationuser")
    public Iterable<AppUser> findAllEmployees() {
        return this.applicationUserRepository.findAll();
    }

    @PostMapping("/applicationuser")
    public AppUser addOneEmployee(@RequestBody AppUser applicationUser) {
        return this.applicationUserRepository.save(applicationUser);
    }

    @GetMapping("admin")
    public String helloAdmin() {
        return "Hello Admin";
    }
}
