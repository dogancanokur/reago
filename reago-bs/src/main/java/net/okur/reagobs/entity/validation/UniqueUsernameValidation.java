package net.okur.reagobs.entity.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import net.okur.reagobs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueUsernameValidation implements ConstraintValidator<UniqueUsername, String> {
  @Autowired
  UserRepository userRepository;

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    var inDb = userRepository.findByUsername(value).orElse(null);
    return inDb == null;
  }
}
