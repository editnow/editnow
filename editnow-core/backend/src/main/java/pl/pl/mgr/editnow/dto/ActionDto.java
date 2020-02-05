package pl.pl.mgr.editnow.dto;

import lombok.Getter;
import lombok.Setter;
import pl.pl.mgr.editnow.dto.action.ActionType;
import java.util.Map;

@Getter
@Setter
public class ActionDto {

  private long id;
  private ActionType actionType;
  private Map<String, Integer> parameters;

}
