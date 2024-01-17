package net.okur.reagobs.configuration;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * This class is used to configure the static resources for the application. It is annotated
 * with @Configuration to indicate that it is a configuration class. It is also annotated
 * with @EnableWebMvc to enable Spring MVC's web support. It implements the WebMvcConfigurer
 * interface to customize Spring MVC's configuration.
 */
@Configuration
@EnableWebMvc
public class StaticResourceConfiguration implements WebMvcConfigurer {
  // for static resources
  /** The AppConfig instance is autowired to access the application's configuration. */
  @Autowired private AppConfig appConfig;

  /**
   * This method is used to add resource handlers for serving static resources. It overrides the
   * addResourceHandlers method of the WebMvcConfigurer interface. It configures a resource handler
   * for serving static resources from a directory in the file system.
   *
   * @param registry the ResourceHandlerRegistry to which the resource handler is added
   */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    String path = Paths.get(appConfig.getStorage().getRoot()).toAbsolutePath() + "/";
    registry
        .addResourceHandler("/assets/**")
        .addResourceLocations("file:" + path)
        .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
  }

  /**
   * This method is used to create the storage directories at application startup. It is annotated
   * with @Bean to indicate that it is a bean to be managed by the Spring container. It returns a
   * CommandLineRunner that creates the root and profile storage directories.
   *
   * @return the CommandLineRunner that creates the storage directories
   */
  @Bean
  CommandLineRunner createStorageDirectories() {
    return args -> {
      // create root
      createFolder(Paths.get(appConfig.getStorage().getRoot()));
      // create profile
      createFolder(
          Paths.get(appConfig.getStorage().getRoot(), appConfig.getStorage().getProfile()));
    };
  }

  /**
   * This method is used to create a directory at the specified path. It checks if the directory
   * already exists, and if not, it creates the directory.
   *
   * @param path the path of the directory to be created
   */
  private void createFolder(Path path) {
    File file = path.toFile();
    boolean isFolderExists = file.exists() && file.isDirectory();

    if (!isFolderExists) {
      file.mkdir();
    }
  }
}
