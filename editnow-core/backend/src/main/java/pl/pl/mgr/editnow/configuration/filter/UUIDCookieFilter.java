package pl.pl.mgr.editnow.configuration.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.pl.mgr.editnow.service.UserService;
import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class UUIDCookieFilter implements Filter {

  private static final String USER_UUID_COOKIE = "userUUID";
  private final UserService userService;

  @Override
  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) req;

    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      log.info("Request path: " + request.getRequestURI());
      setUUIDFromCookiesInContext(cookies);
    }

    chain.doFilter(request, resp);
  }

  private void setUUIDFromCookiesInContext(Cookie[] cookies) {
    Arrays.stream(cookies)
      .filter(cookie -> cookie.getName().equals(USER_UUID_COOKIE))
      .findFirst()
      .ifPresent(uuidCookie -> {
        String uuid = uuidCookie.getValue();

        log.info("Request from user: " + uuid);

        userService.addToDatabaseIfNotExist(uuid);

        UUIDAuthentication uuidAuthentication = new UUIDAuthentication(uuid);
        SecurityContextHolder.getContext().setAuthentication(uuidAuthentication);
      });
  }

}
