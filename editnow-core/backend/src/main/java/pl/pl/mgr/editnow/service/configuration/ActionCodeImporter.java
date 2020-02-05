package pl.pl.mgr.editnow.service.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import pl.pl.mgr.editnow.domain.configuration.ActionCode;
import pl.pl.mgr.editnow.dto.PythonLibrary;
import pl.pl.mgr.editnow.dto.action.ActionType;
import pl.pl.mgr.editnow.repository.ActionCodeRepository;
import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionCodeImporter {

  private static final String FUNCTIONS_CODE_FILE_PATH = "code/actions-code.txt";
  private static final String ACTION_CODE_START_SIGN = "#";

  private final ActionCodeRepository actionCodeRepository;

  private final Map<ActionType, ActionCode> actionCodes = createActionCodes();

  private Map<ActionType, ActionCode> createActionCodes() {
    Object[][] pythonLibrariesInActionType = {
      {ActionType.GRAYSCALE, Collections.singletonList(PythonLibrary.OPEN_CV)},
      {ActionType.ROTATE, Collections.singletonList(PythonLibrary.OPEN_CV)},
      {ActionType.RESIZE, Collections.singletonList(PythonLibrary.OPEN_CV)},
      {ActionType.MEDIAN_BLUR, Collections.singletonList(PythonLibrary.OPEN_CV)},
      {ActionType.GAUSSIAN_BLUR, Collections.singletonList(PythonLibrary.OPEN_CV)},
      {ActionType.BILATERAL_FILTER, Collections.singletonList(PythonLibrary.OPEN_CV)},
      {ActionType.THRESHOLD, Collections.singletonList(PythonLibrary.OPEN_CV)},
      {ActionType.PREWITT, Arrays.asList(PythonLibrary.SCIKIT_PREWITT, PythonLibrary.SCIKIT_IO)},
      {ActionType.ROBERTS, Arrays.asList(PythonLibrary.SCIKIT_ROBERTS, PythonLibrary.SCIKIT_IO)},
      {ActionType.SCHARR, Arrays.asList(PythonLibrary.SCIKIT_SCHARR, PythonLibrary.SCIKIT_IO)},
      {ActionType.SOBEL, Arrays.asList(PythonLibrary.SCIKIT_SOBEL, PythonLibrary.SCIKIT_IO)},
      {ActionType.MORPHOLOGY_TRANSFORM, Arrays.asList(PythonLibrary.OPEN_CV, PythonLibrary.NUMPY)},
    };

    return prepareActionCodes(pythonLibrariesInActionType);
  }

  @SuppressWarnings("unchecked")
  private Map<ActionType, ActionCode> prepareActionCodes(Object[][] actionWithLibraries) {
    return Stream.of(actionWithLibraries)
      .collect(Collectors.toMap(
        actionData -> (ActionType) actionData[0],
        actionData -> {
          ActionCode actionCode = new ActionCode();
          actionCode.setActionType((ActionType) actionData[0]);
          actionCode.setPythonLibraries((List<PythonLibrary>) actionData[1]);

          return actionCode;
        }));
  }

  public void importActionCodes() {
    Resource resource = new ClassPathResource(FUNCTIONS_CODE_FILE_PATH);

    try {
      try ( BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream())) ) {

        fillActionCodes(reader.lines());

        log.info("Action codes import completed");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void fillActionCodes(Stream<String> lines) {
    AtomicReference<ActionType> actualActionType = new AtomicReference<>();
    StringBuilder code = new StringBuilder();
    AtomicBoolean actionCodeExists = new AtomicBoolean(false);

    lines.forEach(codeLine -> {
      if (actionCodeExists.get()) { //action code exists in database - do nothing
        return;
      } else if (codeLine.startsWith(ACTION_CODE_START_SIGN)) {  //found start of next action code
        ActionType foundActionType = ActionType.valueOf(  //point to actual action type
          codeLine.substring(1).replaceAll("\\s+|", ""));

        if (actionCodeForActionTypeExists(foundActionType)) {
          actionCodeExists.set(true);
        } else {
          actionCodeExists.set(false);
          if (actualActionType.get() != null) { //save previous code
            saveActionCode(actualActionType.get(), code.toString());
          }

          actualActionType.set(foundActionType);  //format "# GRAYSCALE" to "GRAYSCALE"

          code.setLength(0);  //reset for next action code block lines
        }
      } else if ( !codeLine.isEmpty()) {  //if is not empty line - add line to code
        code.append(codeLine).append("\n");
      }
    });
    if ( !actionCodeExists.get()) {
      saveActionCode(actualActionType.get(), code.toString()); //save last action
    }
  }

  private boolean actionCodeForActionTypeExists(ActionType foundActionType) {
    return actionCodeRepository.existsActionCodeByActionType(foundActionType);
  }

  private void saveActionCode(ActionType actionType, String code) {
    ActionCode actionCode = actionCodes.get(actionType);
    actionCode.setCode(code);

    actionCodeRepository.save(actionCode);
  }

}
