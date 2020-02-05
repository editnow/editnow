package pl.pl.mgr.editnow.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ImageType {
  PNG("png", new String[]{"png"}),
  JPG("jpg", new String[]{"jpg", "jpeg"}),
  ;

  private final String extension;
  private final String[] sameExtensions;

  @JsonCreator
  public static ImageType fromExtension(String extension) throws Exception {
    for(ImageType imageType: values()) {
      for (String sameExtension: imageType.sameExtensions) {
        if (sameExtension.equals(extension)) {
          return imageType;
        }
      }
    }

    throw new Exception("Image type \"" + extension + "\" is not supported");
  }

  @JsonValue
  public String toExtension() {
    return this.extension;
  }

}
