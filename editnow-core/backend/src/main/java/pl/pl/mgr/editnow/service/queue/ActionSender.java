package pl.pl.mgr.editnow.service.queue;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.pl.mgr.editnow.dto.ActionQueueItem;

@Service
@RequiredArgsConstructor
public class ActionSender implements Sender<ActionQueueItem> {

  @Value("${queue.name.action}")
  private String actionQueueName;

  private final RabbitTemplate rabbitTemplate;

  @Override
  public void send(ActionQueueItem actionQueueItem) {
    String actionJson = new Gson().toJson(actionQueueItem);
    rabbitTemplate.convertAndSend(actionQueueName, actionJson);
  }

}
