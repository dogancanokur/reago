package net.okur.reagobs.error;

import lombok.*;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
  private int status;
  private String message;
  private String path;
  private long timestamp = new Date().getTime();
  private Map<String, String> validationError;

}
