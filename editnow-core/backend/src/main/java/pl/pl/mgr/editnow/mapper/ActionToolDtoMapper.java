package pl.pl.mgr.editnow.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.pl.mgr.editnow.domain.configuration.ActionTool;
import pl.pl.mgr.editnow.dto.ActionToolDto;

@Component
@RequiredArgsConstructor
public class ActionToolDtoMapper implements Mapper<ActionTool, ActionToolDto> {

  private final ParameterInfoDtoMapper parameterInfoDtoMapper;

  public ActionToolDto map(ActionTool actionTool) {
    ActionToolDto actionToolDto = new ActionToolDto();
    actionToolDto.setName(actionTool.getActionType().name());
    actionToolDto.setReadableName(actionTool.getActionType().getReadableName());
    actionToolDto.setParameterInfoDtos(
      parameterInfoDtoMapper.mapList(actionTool.getParameterInfos()));
    actionToolDto.setDisabled(actionTool.isDisabled());

    return actionToolDto;
  }

}
