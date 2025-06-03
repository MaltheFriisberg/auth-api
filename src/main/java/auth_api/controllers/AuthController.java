package auth_api.controllers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import auth_api.exceptions.EmailAlreadyRegisteredException;
import auth_api.exceptions.UserAlreadyRegisteredException;
import auth_api.models.ERole;
import auth_api.models.Role;
import auth_api.models.User;
import auth_api.repository.RoleRepository;
import auth_api.repository.UserRepository;
import auth_api.request.LoginRequest;
import auth_api.request.SignUpRequest;
import auth_api.response.JwtResponse;
import auth_api.response.MessageResponse;
import auth_api.security.jwt.JwtUtils;
import auth_api.security.services.UserDetailsImpl;
import auth_api.service.IAuthService;
import auth_api.service.SignInResult;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    IAuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        SignInResult signInResult = authService.authenticateUser(loginRequest);
        return ResponseEntity
                .ok(new JwtResponse(signInResult.jwt(), signInResult.userDetails().getId(),
                        signInResult.userDetails().getUsername(), signInResult.userDetails().getEmail(), signInResult.roles()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

        try {
            authService.registerUser(signUpRequest);
        } catch (UserAlreadyRegisteredException | EmailAlreadyRegisteredException e ) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
