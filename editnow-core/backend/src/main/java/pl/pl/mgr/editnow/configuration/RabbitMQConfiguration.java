package pl.pl.mgr.editnow.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

  @Value("${queue.name.action}")
  private String actionQueueName;

  @Value("${queue.name.completed-action}")
  private String completedActionName;

  @Bean
  public Queue actionQueue() {
    return new Queue(actionQueueName);
  }

  @Bean
  public Queue completedActionQueue() {
    return new Queue(completedActionName);
  }

}
