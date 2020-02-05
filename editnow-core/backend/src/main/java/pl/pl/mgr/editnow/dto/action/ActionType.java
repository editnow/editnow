package pl.pl.mgr.editnow.dto.action;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActionType {
    GRAYSCALE ("Grayscale"),
    RESIZE ("Resize"),
    ROTATE ("Rotate"),
    MEDIAN_BLUR ("Median blur"),
    GAUSSIAN_BLUR ("Gaussian blur"),
    BILATERAL_FILTER ("Bilateral filter"),
    THRESHOLD ("Thresholding"),
    PREWITT ("Prewitt operator"),
    ROBERTS ("Roberts cross"),
    SCHARR ("Scharr operator"),
    SOBEL ("Sobel operator"),
    MORPHOLOGY_TRANSFORM ("Morphological transform")
    ;

    private final String readableName;

}
