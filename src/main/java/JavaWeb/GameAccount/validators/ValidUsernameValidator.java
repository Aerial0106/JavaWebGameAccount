package JavaWeb.GameAccount.validators;

import JavaWeb.GameAccount.services.UserService;
import JavaWeb.GameAccount.validators.annotations.ValidUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidUsernameValidator implements ConstraintValidator<ValidUsername, String> {
    private UserService userService;

    public ValidUsernameValidator() {
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (userService == null) {
            return true; // Skip validation if the service is not available
        }
        return userService.findByUsername(username).isEmpty();
    }
}