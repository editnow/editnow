package pl.pl.mgr.editnow.dto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PythonLibrary {

  OPEN_CV("cv2", null, null),
  NUMPY("numpy", "np", null),
  SCIKIT_PREWITT("prewitt", null, "skimage.filters"),
  SCIKIT_ROBERTS("roberts", null, "skimage.filters"),
  SCIKIT_SCHARR("scharr", null, "skimage.filters"),
  SCIKIT_SOBEL("sobel", null, "skimage.filters"),
  SCIKIT_IO("io", null, "skimage")
  ;

  private static final String KEYWORD_IMPORT = "import ";
  private static final String KEYWORD_AS = " as ";
  private static final String KEYWORD_FROM = "from ";

  private final String name;
  private final String alias;
  private final String from;

  public String importLine() {
    StringBuilder importLine = new StringBuilder();
    if (from != null) {
      importLine.append(KEYWORD_FROM).append(from).append(" ");
    }
    importLine.append(KEYWORD_IMPORT).append(name);
    if (alias != null) {
      importLine.append(KEYWORD_AS).append(alias);
    }

    return importLine.toString();
  }

}
