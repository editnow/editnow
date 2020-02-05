package pl.pl.mgr.editnow.mapper;

import org.springframework.stereotype.Component;
import pl.pl.mgr.editnow.domain.Parameter;
import pl.pl.mgr.editnow.dto.ParameterDto;

@Component
public class ParameterMapperImpl implements Mapper<ParameterDto, Parameter> {

  @Override
  public Parameter map(ParameterDto parameterDto) {
    Parameter parameter = new Parameter();
    parameter.setName(parameterDto.getName());
    parameter.setParameterType(parameterDto.getParameterType());
    parameter.setValue(parameterDto.getValue());

    return parameter;
  }

}
