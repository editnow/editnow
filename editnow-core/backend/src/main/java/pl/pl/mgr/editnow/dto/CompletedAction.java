package pl.pl.mgr.editnow.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CompletedAction {

  private final long actionId;
  private final String imageBase64;
  private final String errorMsg;

}
