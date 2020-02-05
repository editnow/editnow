package pl.pl.mgr.editnow.dto;

import lombok.Getter;
import lombok.Setter;
import pl.pl.mgr.editnow.dto.configuration.ParameterInfoDto;
import java.util.List;

@Getter
@Setter
public class ActionToolDto {

  private String name;
  private String readableName;
  private List<ParameterInfoDto> parameterInfoDtos;
  private boolean disabled;

}
