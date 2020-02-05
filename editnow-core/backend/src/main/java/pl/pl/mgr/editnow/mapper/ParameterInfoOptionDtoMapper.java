package pl.pl.mgr.editnow.mapper;

import org.springframework.stereotype.Component;
import pl.pl.mgr.editnow.domain.configuration.ParameterInfoOption;
import pl.pl.mgr.editnow.dto.configuration.ParameterInfoOptionDto;

@Component
public class ParameterInfoOptionDtoMapper implements Mapper<ParameterInfoOption, ParameterInfoOptionDto> {

  @Override
  public ParameterInfoOptionDto map(ParameterInfoOption parameterInfoOption) {
    ParameterInfoOptionDto parameterInfoOptionDto = new ParameterInfoOptionDto();
    parameterInfoOptionDto.setText(parameterInfoOption.getText());
    parameterInfoOptionDto.setValue(parameterInfoOption.getValue());

    return parameterInfoOptionDto;
  }

}
