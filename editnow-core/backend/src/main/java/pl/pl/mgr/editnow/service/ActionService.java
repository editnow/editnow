package pl.pl.mgr.editnow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pl.mgr.editnow.domain.Action;
import pl.pl.mgr.editnow.domain.ActionChain;
import pl.pl.mgr.editnow.domain.Image;
import pl.pl.mgr.editnow.domain.User;
import pl.pl.mgr.editnow.dto.ActionDto;
import pl.pl.mgr.editnow.dto.ImageDetails;
import pl.pl.mgr.editnow.dto.action.ActionStatus;
import pl.pl.mgr.editnow.dto.ActionQueueItem;
import pl.pl.mgr.editnow.dto.ActionRequest;
import pl.pl.mgr.editnow.dto.action.ActionType;
import pl.pl.mgr.editnow.mapper.ActionDtoMapper;
import pl.pl.mgr.editnow.mapper.ParameterDtoMapper;
import pl.pl.mgr.editnow.mapper.ParameterMapperImpl;
import pl.pl.mgr.editnow.repository.ActionRepository;
import pl.pl.mgr.editnow.service.queue.ActionSender;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActionService {

  private final ActionDtoMapper actionDtoMapper;
  private final ParameterMapperImpl parameterMapperImpl;
  private final ParameterDtoMapper parameterDtoMapper;
  private final UserService userService;
  private final ImageService imageService;

  private final ActionSender actionSender;

  private final ActionRepository actionRepository;

  @Transactional
  public Action completeAction(long actionId, boolean isError) {
    Action action = getAction(actionId);
    if (isError) {
      action.setStatus(ActionStatus.ERROR);
    } else {
      action.setStatus(ActionStatus.COMPLETED);
    }

    return action;
  }

  @Transactional
  public ActionDto startAction(ActionRequest actionRequest) throws IOException {
    User user = userService.getUserFromContext();

    Action action = createAction(actionRequest, user);

    addActionToActionChain(user, action);

    ActionQueueItem actionQueueItem = createActionQueueItem(action, actionRequest.getImageBase64());
    actionSender.send(actionQueueItem);

    return actionDtoMapper.map(action);
  }

  private void addActionToActionChain(User user, Action action) {
    ActionChain actionChain = user.getActionChain();
    if (actionChain == null) {
      actionChain = new ActionChain();
      actionChain.setActions(Collections.singletonList(action));
      user.setActionChain(actionChain);
    } else {
      actionChain.getActions().add(action);
    }
  }

  private ActionQueueItem createActionQueueItem(Action action, String imageBase64) throws IOException {
    String inputImageName = action.getInputImage().getName();

    return ActionQueueItem.builder()
      .actionId(action.getId())
      .actionName(action.getActionType().name())
      .inputImageName(inputImageName)
      .outputImageName(action.getOutputImage().getName())
      .imageBase64(imageBase64 != null
        ? imageBase64
        : imageService.getBase64(inputImageName))
      .parameterDtos(parameterDtoMapper.mapList(action.getParameters()))
      .build();
  }

  private Action createAction(ActionRequest actionRequest, User user) {
    Image inputImage = getInputImage(actionRequest, user);
    Image outputImage = imageService.createOutputImageMetadata(inputImage.getType());

    Action action = new Action();
    action.setActionType(actionRequest.getActionType());
    action.setParameters(parameterMapperImpl.mapList(actionRequest.getParameters()));
    action.setInputImage(inputImage);
    action.setOutputImage(outputImage);
    action.setStatus(ActionStatus.PENDING);
    action.setUser(user);
    actionRepository.save(action);

    return action;
  }

  private Image getInputImage(ActionRequest actionRequest, User user) {
    List<Action> completedActions = getOnlyCompletedActions(user);  //without errors because output image doesnt exists

    return isNextActionFromChain(completedActions)
      ? getOutputImageFromLastActionInChain(completedActions)
      : imageService.saveInputImage(actionRequest);
  }

  private List<Action> getOnlyCompletedActions(User user) {
    return user.getActionChain().getActions().stream()
      .filter(action -> action.getStatus().equals(ActionStatus.COMPLETED))
      .collect(Collectors.toList());
  }

  private Image getOutputImageFromLastActionInChain(List<Action> actionChain) {
    int lastActionFromChainIndex = actionChain.size() - 1;

    return actionChain.get(lastActionFromChainIndex)
      .getOutputImage();
  }

  private boolean isNextActionFromChain(List<Action> actionChain) {
    return actionChain != null && actionChain.size() > 0;
  }

  @Transactional
  public boolean newActionChain() {
    ActionChain actionChain = new ActionChain();
    actionChain.setActions(new ArrayList<>());

    User user = userService.getUserFromContext();
    user.setActionChain(actionChain);

    return true;
  }

  public Action getAction(long id) {
    return actionRepository.findById(id);
//      .orElseThrow(() -> new Exception("Action id = " + id + " not found"));
  }

  public ImageDetails getOutputImageByActionId(long actionId) throws IOException {
    Action action = actionRepository.findById(actionId);

    String outputImageName = action.getOutputImage().getName();

    return imageService.getBase64ImageDetails(outputImageName);
  }

  public boolean actionExists(long actionId) {
    return actionRepository.findById(actionId) != null;
  }

}
