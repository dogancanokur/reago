package net.okur.reagobs.entity.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import net.okur.reagobs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class implements the ConstraintValidator interface for the UniqueEmail annotation. It
 * validates that an email is unique by checking if it exists in the UserRepository.
 *
 * <p>The class is autowired with a UserRepository instance.
 */
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

  private final UserRepository userRepository;

  /**
   * Constructs a new UniqueEmailValidator with the specified UserRepository.
   *
   * @param userRepository the UserRepository to be used for email validation
   */
  @Autowired
  public UniqueEmailValidator(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Checks if the specified email is unique.
   *
   * @param value the email to be checked
   * @param context the context in which the constraint is evaluated
   * @return true if the email is unique, false otherwise
   */
  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return userRepository.findByEmail(value).isEmpty();
  }
}
