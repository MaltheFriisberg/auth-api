package auth_api.service;

import auth_api.security.services.UserDetailsImpl;
import org.springframework.security.core.Authentication;

import java.util.List;

public record SignInResult(String jwt, UserDetailsImpl userDetails, List<String> roles) {}
