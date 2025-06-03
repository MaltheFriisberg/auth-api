package auth_api.exceptions;

public class UserAlreadyRegisteredException extends Exception {
    public UserAlreadyRegisteredException(String errorMessage) {
        super(errorMessage);
    }
}
