package pl.pl.mgr.editnow.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pl.mgr.editnow.dto.ImageType;
import pl.pl.mgr.editnow.service.util.ImageNameFactoryImpl;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileStorageService {

  @Value("${directory.images}")
  private String IMAGES_DIRECTORY = "./images/";

  private final ImageNameFactoryImpl imageNameFactory;

  @EventListener(ApplicationReadyEvent.class)
  public void createDirectories() {
    boolean createdDirectories = new File(IMAGES_DIRECTORY).mkdirs();
    if (createdDirectories) {
      log.info("Path '" + IMAGES_DIRECTORY + "' created");
    }
  }

  @Transactional
  public String saveRenamedToUUID(String imageBase64, ImageType imageType) {
    String fileName = imageNameFactory.generateName(imageType);
    return saveImageFromBase64(imageBase64, fileName);
  }

  public String saveImageFromBase64(String imageBase64, String imageName) {
    Path filePath = Paths.get(IMAGES_DIRECTORY, imageName);

    try {
      byte[] decodedImageBytes = Base64.getDecoder().decode(imageBase64.getBytes(StandardCharsets.UTF_8));
      Files.write(filePath, decodedImageBytes);
      return imageName;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }


  public String loadImageInBase64(String imageName) throws IOException {
    byte[] imageBytes = Files.readAllBytes(Paths.get(IMAGES_DIRECTORY, imageName));

    return Base64.getEncoder().encodeToString(imageBytes);
  }

}
