 package JavaWeb.GameAccount.validators;
 import JavaWeb.GameAccount.services.UserService;
 import JavaWeb.GameAccount.validators.annotations.*;

 import jakarta.validation.ConstraintValidator;
 import jakarta.validation.ConstraintValidatorContext;
 import lombok.RequiredArgsConstructor;
 import org.springframework.stereotype.Component;

 @Component
 @RequiredArgsConstructor
 public class ValidUsernameValidator implements
         ConstraintValidator<ValidUsername, String> {
     private final UserService userService;

     @Override
     public boolean isValid(String username, ConstraintValidatorContext
             context) {
         return userService.findByUsername(username).isEmpty();
     }
 }