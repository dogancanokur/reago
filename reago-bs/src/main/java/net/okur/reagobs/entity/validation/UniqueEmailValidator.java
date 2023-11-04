package net.okur.reagobs.entity.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import net.okur.reagobs.entity.User;
import net.okur.reagobs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

  @Autowired UserRepository userRepository;

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    User byEmail = userRepository.findByEmail(value);
    return byEmail == null;
  }
}
