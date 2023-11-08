package net.okur.reagobs.file;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import net.okur.reagobs.configuration.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FileService {
  private final AppConfig appConfig;

  @Autowired
  public FileService(AppConfig appConfig) {
    this.appConfig = appConfig;
  }

  public String saveBase64StringAsFile(String base64image) {

    try {
      String fileName = UUID.randomUUID().toString();
      Path path =
          Paths.get(
              appConfig.getStorage().getRoot(), //
              appConfig.getStorage().getProfile(), //
              fileName);

      OutputStream outputStream = new FileOutputStream(path.toFile());
      byte[] base64decoded = Base64.getDecoder().decode(base64image.split(",")[1]);

      outputStream.write(base64decoded);
      outputStream.close();
      return fileName;

    } catch (IOException e) {
      log.error(e.getMessage());
      e.printStackTrace();
    }
    return null;
  }
}
