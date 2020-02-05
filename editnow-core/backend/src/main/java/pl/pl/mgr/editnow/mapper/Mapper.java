package pl.pl.mgr.editnow.mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public interface Mapper<FROM, TO> {

  TO map(FROM fromDto);

  default List<TO> mapList(List<FROM> fromDtos) {
    if (fromDtos == null) {
      return Collections.emptyList();
    }
    return fromDtos.stream()
      .map(this::map)
      .collect(Collectors.toList());
  }

}
