package net.okur.reagobs.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ApiError {
  private int status;
  private String message;
  private String path;
  private long timestamp = new Date().getTime();
  private Map<String, String> validationError;

}
