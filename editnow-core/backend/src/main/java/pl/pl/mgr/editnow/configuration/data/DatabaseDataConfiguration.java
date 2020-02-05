package pl.pl.mgr.editnow.configuration.data;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.pl.mgr.editnow.service.configuration.ActionCodeImporter;
import pl.pl.mgr.editnow.service.configuration.ActionToolConfiguration;

@Component
@RequiredArgsConstructor
public class DatabaseDataConfiguration {

  private final ActionCodeImporter actionCodeImporter;
  private final ActionToolConfiguration actionToolConfiguration;

  @EventListener(ApplicationReadyEvent.class)
  public void createDirectoriesAndMockData() {
    actionCodeImporter.importActionCodes();
    actionToolConfiguration.createActionToolConfiguration();
  }

}
