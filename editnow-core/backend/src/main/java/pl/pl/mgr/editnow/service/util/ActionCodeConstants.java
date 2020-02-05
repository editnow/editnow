package pl.pl.mgr.editnow.service.util;

import org.springframework.stereotype.Component;
import pl.pl.mgr.editnow.dto.PythonLibrary;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class ActionCodeConstants {

  public static final String LOAD_IMAGE_IN_OPEN_CV = "image = cv2.imread(\"path/to/load/your_image.jpg\")\n\n";
  public static final String LOAD_IMAGE_IN_SCIKIT = "image = io.imread(\"path/to/load/your_image.jpg\")\n\n";
  public static final String SAVE_IMAGE_IN_OPEN_CV = "cv2.imwrite(\"path/to/save/output_image.jpg\", image)\n\n";
  public static final String SAVE_IMAGE_IN_SCIKIT = "io.imsave(\"path/to/save/output_image.jpg\", image)\n\n";
  public static final List<PythonLibrary> BGR_LIBRARIES = Collections.singletonList(PythonLibrary.OPEN_CV);
  public static final List<PythonLibrary> RGB_LIBRARIES = Arrays.asList(PythonLibrary.SCIKIT_SOBEL, PythonLibrary.SCIKIT_ROBERTS,
    PythonLibrary.SCIKIT_PREWITT, PythonLibrary.SCIKIT_SCHARR, PythonLibrary.SCIKIT_IO);
  public static final String CONVERSION_BGR_RGB_OR_RGB_BGR = "if len(image.shape) == 3:\n    image = image[...,::-1]\n";

}
