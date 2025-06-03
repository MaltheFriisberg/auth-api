package auth_api.service;

import auth_api.request.LoginRequest;
import auth_api.request.SignUpRequest;

public interface IAuthService {
    SignInResult authenticateUser(LoginRequest loginRequest);
    void registerUser(SignUpRequest signUpRequest);

}
