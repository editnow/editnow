package pl.pl.mgr.editnow.service.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.pl.mgr.editnow.domain.Image;
import pl.pl.mgr.editnow.dto.ImageType;
import pl.pl.mgr.editnow.repository.ImageRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImageFactory {

  private final ImageRepository imageRepository;
  private final ImageNameFactoryImpl imageNameFactory;

  public Image getInputImage(String imageName, ImageType imageType) {
    return createImage(imageName, imageType);
  }

  public Image createOutputImageMetadata(ImageType imageType) {
    String imageName = imageNameFactory.generateName(imageType);

    return createImage(imageName, imageType);
  }

  private Image createImage(String imageName, ImageType imageType) {
    Image image = new Image();
    image.setName(imageName);
    image.setType(imageType);
    imageRepository.save(image);

    return image;
  }

}
