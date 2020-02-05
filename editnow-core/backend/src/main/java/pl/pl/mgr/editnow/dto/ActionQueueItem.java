package pl.pl.mgr.editnow.dto;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class ActionQueueItem {

  private final long actionId;
  private final String actionName;
  private final String inputImageName;
  private final String outputImageName;
  private final String imageBase64;
  private final List<ParameterDto> parameterDtos;

}
