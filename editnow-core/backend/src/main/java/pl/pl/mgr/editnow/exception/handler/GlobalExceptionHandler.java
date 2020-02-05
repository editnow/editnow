package pl.pl.mgr.editnow.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import java.sql.SQLSyntaxErrorException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({ SQLSyntaxErrorException.class })
  public ResponseEntity<String> handleSqlSyntaxErrorException(Exception ex, WebRequest request) {

    String message = "Problem z SQL: " + ex.getMessage();

    return handleExceptionInternal(message, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, ex);
  }

  private ResponseEntity<String> handleExceptionInternal(String message, HttpHeaders httpHeaders, HttpStatus httpStatus, Exception ex) {
    log.error("Handle exception: " + ex.getClass().getSimpleName() + " | message: " + message);

    return new ResponseEntity<>(message, httpHeaders, httpStatus);
  }


}
