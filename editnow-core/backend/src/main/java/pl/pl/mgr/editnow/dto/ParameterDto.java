package pl.pl.mgr.editnow.dto;

import lombok.Getter;
import lombok.Setter;
import pl.pl.mgr.editnow.dto.configuration.ParameterType;

@Getter
@Setter
public class ParameterDto {

  private String name;
  private ParameterType parameterType;
  private String value;

}
