package pl.pl.mgr.editnow.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.pl.mgr.editnow.dto.ActionDto;
import pl.pl.mgr.editnow.dto.ActionRequest;
import pl.pl.mgr.editnow.dto.ImageDetails;
import pl.pl.mgr.editnow.service.ActionCodeService;
import pl.pl.mgr.editnow.service.ActionService;
import java.io.IOException;

@RestController
@RequestMapping("/api/action")
@RequiredArgsConstructor
public class ActionController {

  private final ActionService actionService;
  private final ActionCodeService actionCodeService;

  @PostMapping
  public ActionDto startAction(@RequestBody ActionRequest actionRequest) throws IOException {
    return actionService.startAction(actionRequest);
  }

  @GetMapping("/{actionId}/output")
  public ImageDetails getOutputImageByActionId(@PathVariable long actionId) throws IOException {
    return actionService.getOutputImageByActionId(actionId);
  }

  @PostMapping("/chain/new")
  public boolean newActionChain() {
    return actionService.newActionChain();
  }

  @GetMapping("/code/generate")
  public String generateCodeFromActionChain() {
    return actionCodeService.generateCodeFromActionChain();
  }


}
