package pl.pl.mgr.editnow.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import pl.pl.mgr.editnow.service.SseEmitterService;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/sse")
@RequiredArgsConstructor
public class SseEmitterController {

  private final SseEmitterService sseEmitterService;

  @GetMapping(path = "/completed-action")
  public SseEmitter getCompletedActionEmitter(HttpServletResponse response) {
    response.setHeader("Cache-Control", "no-store");
    SseEmitter emitter = new SseEmitter();
    sseEmitterService.addEmitterForUser(emitter);

    emitter.onCompletion(sseEmitterService::removeEmitter);
    emitter.onTimeout(() -> {
      emitter.complete();
      sseEmitterService.removeEmitter();
    });

    return emitter;
  }

  @DeleteMapping("/completed-action/close")
  public void closeCompletedActionEmitter() {
    sseEmitterService.removeEmitter();
  }

}
