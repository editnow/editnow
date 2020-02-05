package pl.pl.mgr.editnow.dto.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActionToolCategory {
  PREPROCESSING("Pre-processing"),
  SEGMENTATION("Segmentation"),
  DETECT_EDGES("Edge detection"),
  MORPHOLOGY_OPERATIONS("Morphological operations")
  ;

  private final String name;

}
