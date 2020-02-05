package pl.pl.mgr.editnow.service.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pl.mgr.editnow.domain.configuration.ActionTool;
import pl.pl.mgr.editnow.dto.ActionToolsInCategoryDto;
import pl.pl.mgr.editnow.dto.configuration.ActionToolCategory;
import pl.pl.mgr.editnow.mapper.ActionToolDtoMapper;
import pl.pl.mgr.editnow.repository.ActionToolRepository;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConfigurationService {

  private final ActionToolRepository actionToolRepository;
  private final ActionToolDtoMapper actionToolDtoMapper;

  public List<ActionToolsInCategoryDto> getActionsTools() {
    List<ActionToolsInCategoryDto> actionToolsInCategoryDtos = new ArrayList<>();

    for (ActionToolCategory actionToolCategory: ActionToolCategory.values()) {
      List<ActionTool> actionToolsFromCategory = actionToolRepository.findActionToolsByActionToolCategory(actionToolCategory);

      ActionToolsInCategoryDto actionToolsInCategoryDto = new ActionToolsInCategoryDto();
      actionToolsInCategoryDto.setCategoryName(actionToolCategory.getName());
      actionToolsInCategoryDto.setActionTools(actionToolDtoMapper.mapList(actionToolsFromCategory));

      actionToolsInCategoryDtos.add(actionToolsInCategoryDto);
    }

    return actionToolsInCategoryDtos;
  }

}
