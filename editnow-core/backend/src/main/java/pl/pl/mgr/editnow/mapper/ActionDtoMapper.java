package pl.pl.mgr.editnow.mapper;

import org.springframework.stereotype.Component;
import pl.pl.mgr.editnow.domain.Action;
import pl.pl.mgr.editnow.dto.ActionDto;

@Component
public class ActionDtoMapper implements Mapper<Action, ActionDto> {

  @Override
  public ActionDto map(Action action) {
    ActionDto actionDto = new ActionDto();
    actionDto.setId(action.getId());
    actionDto.setActionType(action.getActionType());

    return actionDto;
  }

}
