package net.okur.reagobs.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
import java.util.Map;
import lombok.*;

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
