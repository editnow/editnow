package pl.pl.mgr.editnow.domain;

import lombok.Getter;
import lombok.Setter;
import pl.pl.mgr.editnow.dto.configuration.ParameterType;
import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
public class Parameter {

  private String name;
  private ParameterType parameterType;
  private String value;

}
