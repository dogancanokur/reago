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

@Configuration
@EnableWebMvc
public class StaticResourceConfiguration implements WebMvcConfigurer {
  // static resourcelar icin
  @Autowired private AppConfig appConfig;

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    String path = Paths.get(appConfig.getStorage().getRoot()).toAbsolutePath() + "/";
    registry
        .addResourceHandler("/assets/**")
        .addResourceLocations("file:" + path)
        .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
  }

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

  private void createFolder(Path path) {
    File file = path.toFile();
    boolean isFolderExists = file.exists() && file.isDirectory();

    if (!isFolderExists) {
      file.mkdir();
    }
  }
}
