package pl.pl.mgr.editnow.mapper;

import org.springframework.stereotype.Component;
import pl.pl.mgr.editnow.domain.Parameter;
import pl.pl.mgr.editnow.dto.ParameterDto;

@Component
public class ParameterDtoMapper implements Mapper<Parameter, ParameterDto> {

  @Override
  public ParameterDto map(Parameter parameter) {
    ParameterDto parameterDto = new ParameterDto();
    parameterDto.setName(parameter.getName());
    parameterDto.setParameterType(parameter.getParameterType());
    parameterDto.setValue(parameter.getValue());

    return parameterDto;
  }

}
