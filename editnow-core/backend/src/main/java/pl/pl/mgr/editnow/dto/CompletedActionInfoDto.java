package pl.pl.mgr.editnow.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CompletedActionInfoDto {

  private long actionId;
  private boolean error;
  private String errorMsg;

}
