package net.okur.reagobs.file;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import net.okur.reagobs.configuration.AppConfig;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FileService {
  private final AppConfig appConfig;

  Tika tika = new Tika();

  @Autowired
  public FileService(AppConfig appConfig) {
    this.appConfig = appConfig;
  }

  /**
   * This method is used to save a Base64 string as a file.
   *
   * @param base64image The Base64 string to be saved as a file. The string should be in the format
   *     "data:image/png;base64,iVBORw0KGg...."
   * @return The name of the file in which the Base64 string has been saved. If an error occurs
   *     during the process, null is returned.
   */
  public String saveBase64StringAsFile(String base64image) {

    try {
      // Generate a random file name using UUID
      String fileName = UUID.randomUUID().toString();

      // Construct the path where the file will be saved
      Path path =
          Paths.get(
              appConfig.getStorage().getRoot(), // The root directory for storage
              appConfig.getStorage().getProfile(), // The profile directory under the root directory
              fileName); // The file name

      // Create an output stream for the file
      OutputStream outputStream = new FileOutputStream(path.toFile());

      // Decode the Base64 string. The actual image data is after the comma in the Base64 string
      byte[] base64decoded = decodedImage(base64image);

      // Write the decoded Base64 string to the file
      outputStream.write(base64decoded);

      // Close the output stream
      outputStream.close();

      // Return the file name
      return fileName;

    } catch (IOException e) {
      // Log the error message and print the stack trace if an error occurs
      log.error(e.getMessage());
      e.printStackTrace();
    }

    // Return null if an error occurs
    return null;
  }

  public String detectType(String value) {
    return tika.detect(decodedImage(value));
  }

  private byte[] decodedImage(String encodedImage) {
    return Base64.getDecoder().decode(encodedImage.split(",")[1]);
  }
}
