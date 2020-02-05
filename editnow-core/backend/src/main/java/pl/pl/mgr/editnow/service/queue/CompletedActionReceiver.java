package pl.pl.mgr.editnow.service.queue;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pl.pl.mgr.editnow.domain.Action;
import pl.pl.mgr.editnow.dto.CompletedAction;
import pl.pl.mgr.editnow.service.ActionService;
import pl.pl.mgr.editnow.service.FileStorageService;
import pl.pl.mgr.editnow.service.SseEmitterService;
import java.nio.charset.StandardCharsets;

@Service
@RabbitListener(queues = "${queue.name.completed-action}")
@RequiredArgsConstructor
public class CompletedActionReceiver {

  private final FileStorageService fileStorageService;
  private final SseEmitterService sseEmitterService;
  private final ActionService actionService;

  @RabbitHandler
  public void receive(String subscribedWordJson) {
    sendCompletedAction(subscribedWordJson);
  }

  @RabbitHandler
  public void receive(byte[] subscribedWordBytes) {
    String subscribedWordJson = new String(subscribedWordBytes, StandardCharsets.UTF_8);

    sendCompletedAction(subscribedWordJson);
  }

  private void sendCompletedAction(String subscribedWordJson) {
    CompletedAction completedAction = new Gson().fromJson(subscribedWordJson, CompletedAction.class);
    boolean isError = completedAction.getErrorMsg() != null && !completedAction.getErrorMsg().isEmpty();

    if (actionService.actionExists(completedAction.getActionId())) {
      Action action = actionService.completeAction(completedAction.getActionId(), isError);
      if (!isError) {
        fileStorageService.saveImageFromBase64(completedAction.getImageBase64(), action.getOutputImage().getName());
      }
      sseEmitterService.sendCompletedAction(completedAction, isError);
    }
  }

}
