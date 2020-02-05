package pl.pl.mgr.editnow.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pl.mgr.editnow.dto.ActionToolsInCategoryDto;
import pl.pl.mgr.editnow.service.configuration.ConfigurationService;
import java.util.List;

@RestController
@RequestMapping("/api/configuration")
@RequiredArgsConstructor
public class ConfigurationController {

  private final ConfigurationService configurationService;

  @GetMapping("/action-tools")
  public List<ActionToolsInCategoryDto> getActionTools() {
    return configurationService.getActionsTools();
  }

}
