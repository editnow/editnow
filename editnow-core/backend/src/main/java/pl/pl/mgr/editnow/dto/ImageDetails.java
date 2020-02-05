package pl.pl.mgr.editnow.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ImageDetails {

  private final String base64;
  private final ImageType type;

}
