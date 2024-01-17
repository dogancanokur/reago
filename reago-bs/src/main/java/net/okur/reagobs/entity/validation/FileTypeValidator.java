package net.okur.reagobs.entity.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import net.okur.reagobs.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class FileTypeValidator implements ConstraintValidator<FileType, String> {
  @Autowired FileService fileService;

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (!StringUtils.hasText(value)) return false;
    String type = fileService.detectType(value);
    return "image/png".equals(type) || "image/jpg".equals(type) || "image/jpeg".equals(type);
  }
}
