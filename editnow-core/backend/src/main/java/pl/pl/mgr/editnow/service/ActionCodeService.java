package pl.pl.mgr.editnow.service;

import static pl.pl.mgr.editnow.service.util.ActionCodeConstants.*;

import lombok.RequiredArgsConstructor;
import org.openide.util.MapFormat;
import org.springframework.stereotype.Service;
import pl.pl.mgr.editnow.domain.Action;
import pl.pl.mgr.editnow.domain.Parameter;
import pl.pl.mgr.editnow.domain.configuration.ActionCode;
import pl.pl.mgr.editnow.domain.User;
import pl.pl.mgr.editnow.dto.PythonLibrary;
import pl.pl.mgr.editnow.dto.action.ActionType;
import pl.pl.mgr.editnow.repository.ActionCodeRepository;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActionCodeService {

  private static final String NEW_LINE = "\n";

  private final UserService userService;
  private final ActionCodeRepository actionCodeRepository;

  public String generateCodeFromActionChain() {
    User user = userService.getUserFromContext();

    List<Action> actions = user.getActionChain().getActions();

    return prepareGeneratedCode(actions);
  }

  private String prepareGeneratedCode(List<Action> actions) {
    StringBuilder generatedCode = new StringBuilder();

    appendLoadImage(generatedCode, actions.get(0));

    Set<PythonLibrary> importedLibraries = new HashSet<>();

    for (int i = 0; i < actions.size(); i++) {
      Action action = actions.get(i);
      ActionType actionType = action.getActionType();
      ActionCode actionCode = actionCodeRepository.findActionCodeByActionType(actionType);

      importedLibraries.addAll(actionCode.getPythonLibraries());  //SET type of collections - add only unique libraries

      String code = prepareCode(actionCode, action.getParameters());

      generatedCode.append("# Action name: ").append(actionType.name()).append(NEW_LINE);
      generatedCode.append(code).append(NEW_LINE);
      if (i < (actions.size() - 1)) {
        appendImageConversion(generatedCode, actions.get(i), actions.get(i + 1));
      }
    }

    insertImports(generatedCode, importedLibraries);
    appendSaveImage(generatedCode, importedLibraries);  //appendSaveImage(generatedCode, actions.get(actions.size() - 1));

    return generatedCode.toString();
  }

  private void appendImageConversion(StringBuilder generatedCode, Action actualAction, Action nextAction) {
    boolean isActualBGRLibrary = isBGRLibrary(actualAction);
    boolean isNextRGBLibrary = isRGBLibrary(nextAction);

    boolean needBGRToRGBConversion = isActualBGRLibrary && isNextRGBLibrary;
    boolean needRGBToBGRConversion = !isActualBGRLibrary && !isNextRGBLibrary;

    if (needBGRToRGBConversion) {
      generatedCode.append("# Image conversion BGR to RGB").append(NEW_LINE);
    }

    if (needRGBToBGRConversion) {
      generatedCode.append("# Image conversion RGB to BGR").append(NEW_LINE);
    }

    if (needBGRToRGBConversion || needRGBToBGRConversion) {
      generatedCode.append(CONVERSION_BGR_RGB_OR_RGB_BGR).append(NEW_LINE);
    }
  }

  private boolean isBGRLibrary(Action action) {
    return findActionCodeForAction(action).getPythonLibraries()
      .stream()
      .map(BGR_LIBRARIES::contains)
      .findFirst()
      .orElse(false);
  }

  private boolean isRGBLibrary(Action action) {
    return findActionCodeForAction(action).getPythonLibraries()
      .stream()
      .map(RGB_LIBRARIES::contains)
      .findFirst()
      .orElse(false);
  }

  private void appendLoadImage(StringBuilder generatedCode, Action action) {
    ActionCode actionCode = findActionCodeForAction(action);

    String loadImageCode = containsOpenCvLibrary(actionCode.getPythonLibraries())
      ? LOAD_IMAGE_IN_OPEN_CV
      : LOAD_IMAGE_IN_SCIKIT;

    generatedCode.append(loadImageCode);
  }

  private void appendSaveImage(StringBuilder generatedCode, Action action) {
    ActionCode actionCode = findActionCodeForAction(action);

    String saveImageCode = containsOpenCvLibrary(actionCode.getPythonLibraries())
      ? SAVE_IMAGE_IN_OPEN_CV
      : SAVE_IMAGE_IN_SCIKIT;

    generatedCode.append(saveImageCode);
  }


  private void appendSaveImage(StringBuilder generatedCode, Set<PythonLibrary> importedLibraries) {
    String saveImageCode = importedLibraries.contains(PythonLibrary.SCIKIT_IO)
      ? SAVE_IMAGE_IN_SCIKIT
      : SAVE_IMAGE_IN_OPEN_CV;

    generatedCode.append(saveImageCode);
  }

  private ActionCode findActionCodeForAction(Action action) {
    return actionCodeRepository.findActionCodeByActionType(action.getActionType());
  }

  private boolean containsOpenCvLibrary(List<PythonLibrary> pythonLibraries) {
    return pythonLibraries.contains(PythonLibrary.OPEN_CV);
  }

  private String prepareCode(ActionCode actionCode, List<Parameter> parameters) {
    String code = actionCode.getCode();
    Map<String, String> valuePerKeyParameters = prepareParameters(parameters);

    if (parameters.size() > 0) {
      code = MapFormat.format(code, valuePerKeyParameters);
    }

    return code;
  }

  private Map<String, String> prepareParameters(List<Parameter> parameters) {
    return parameters.stream()
      .collect(Collectors.toMap(
        Parameter::getName,
        Parameter::getValue));
  }

  private void insertImports(StringBuilder generatedCode, Set<PythonLibrary> importedLibraries) {
    StringBuilder generatedImports = new StringBuilder();
    importedLibraries.forEach(libraryImport ->
      generatedImports.append(libraryImport.importLine()).append(NEW_LINE));
    generatedImports.append(NEW_LINE);

    generatedCode.insert(0, generatedImports.toString());
  }

}
