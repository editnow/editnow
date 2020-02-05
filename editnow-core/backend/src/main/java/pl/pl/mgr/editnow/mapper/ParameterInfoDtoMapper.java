package pl.pl.mgr.editnow.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.pl.mgr.editnow.domain.configuration.ParameterInfo;
import pl.pl.mgr.editnow.dto.configuration.ParameterInfoDto;

@Component
@RequiredArgsConstructor
public class ParameterInfoDtoMapper implements Mapper<ParameterInfo, ParameterInfoDto> {

  private final ParameterInfoOptionDtoMapper parameterInfoOptionDtoMapper;

  @Override
  public ParameterInfoDto map(ParameterInfo parameterInfo) {
    ParameterInfoDto parameterInfoDto = new ParameterInfoDto();
    parameterInfoDto.setName(parameterInfo.getName());
    parameterInfoDto.setParameterType(parameterInfo.getParameterType());
    parameterInfoDto.setTips(parameterInfo.getTips());
    parameterInfoDto.setParameterInfoOptionDtos(
      parameterInfoOptionDtoMapper.mapList(parameterInfo.getParameterInfoOptions()));

    return parameterInfoDto;
  }

}
