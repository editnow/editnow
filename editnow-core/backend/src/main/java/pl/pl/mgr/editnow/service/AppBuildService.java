package pl.pl.mgr.editnow.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class AppBuildService {

  @Value("${editnow.version}")
  private String editnowVersion;

  @Value("${editnow.build.timestamp}")
  private String editnowBuildTimestamp;


  public String getBuildInfo() {
    try {
      ZonedDateTime zonedDateTime = ZonedDateTime.parse(editnowBuildTimestamp);
      String parsedDate = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy").format(zonedDateTime);
      return "Version: " + editnowVersion + " | Build date: " + parsedDate;
    } catch(DateTimeParseException exc) {

    }
    return "IDE compilation";
  }
}
