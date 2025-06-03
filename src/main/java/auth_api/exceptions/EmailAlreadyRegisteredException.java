package auth_api.exceptions;

public class EmailAlreadyRegisteredException extends Exception {
    public EmailAlreadyRegisteredException(String errorMessage) {
        super(errorMessage);
    }
}
