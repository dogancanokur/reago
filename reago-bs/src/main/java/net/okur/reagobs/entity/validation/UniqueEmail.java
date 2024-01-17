package net.okur.reagobs.entity.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * This annotation is used to validate that an email is unique. It is used on fields and is retained
 * at runtime. The validation is performed by the UniqueEmailValidator class.
 *
 * <p>The default error message key is "warning.user.email.already-used".
 *
 * <p>The groups() method can be used to specify validation groups. The payload() method can be used
 * to specify custom payload classes.
 */
@Constraint(validatedBy = {UniqueEmailValidator.class})
@Target({FIELD})
@Retention(RUNTIME)
public @interface UniqueEmail {

  /**
   * Specifies the error message key. The default value is "warning.user.email.already-used".
   *
   * @return the error message key
   */
  String message() default "{warning.user.email.already-used}";

  /**
   * Specifies the validation groups. The default value is an empty array.
   *
   * @return the validation groups
   */
  Class<?>[] groups() default {};

  /**
   * Specifies custom payload classes. The default value is an empty array.
   *
   * @return the custom payload classes
   */
  Class<? extends Payload>[] payload() default {};
}
