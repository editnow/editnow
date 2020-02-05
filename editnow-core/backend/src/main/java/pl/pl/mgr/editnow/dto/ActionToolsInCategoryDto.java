package pl.pl.mgr.editnow.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ActionToolsInCategoryDto {

  private String categoryName;
  private List<ActionToolDto> actionTools;

}
