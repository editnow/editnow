package pl.pl.mgr.editnow.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pl.mgr.editnow.service.AppBuildService;

@RestController
@RequestMapping("/api/app/info")
@RequiredArgsConstructor
public class AppInfoController {

  private final AppBuildService appBuildService;

  @GetMapping("/build")
  public String getAppBuildInfo() {
    return appBuildService.getBuildInfo();
  }

}
