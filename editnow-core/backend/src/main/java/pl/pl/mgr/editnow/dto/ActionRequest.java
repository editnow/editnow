package pl.pl.mgr.editnow.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.pl.mgr.editnow.dto.action.ActionType;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class ActionRequest {

  private final ActionType actionType;
  private final String imageBase64;
  private final ImageType imageType;
  private final List<ParameterDto> parameters;

}
