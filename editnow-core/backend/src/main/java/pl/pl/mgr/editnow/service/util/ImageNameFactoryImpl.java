package pl.pl.mgr.editnow.service.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.pl.mgr.editnow.dto.ImageType;
import pl.pl.mgr.editnow.repository.ImageRepository;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImageNameFactoryImpl implements ImageNameFactory {

  private static final String STRING_WILDCARD = "%";

  private final ImageRepository imageRepository;

  @Override
  public String generateName(ImageType imageType) {
    return generateUniqueImageName(imageType.getExtension());
  }

  private String generateUniqueImageName(String imageTypeExtension) {
    String uniqueImageName = getRandomUUID();
    while (imageRepository.existsImageByNameLike(uniqueImageName + STRING_WILDCARD)) {
      uniqueImageName = getRandomUUID();
      log.debug("Generated new unique image name");
    }
    return uniqueImageName + "." + imageTypeExtension;
  }

  private String getRandomUUID() {
    return UUID.randomUUID().toString();
  }

}
