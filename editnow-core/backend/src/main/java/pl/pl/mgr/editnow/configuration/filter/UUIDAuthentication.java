package pl.pl.mgr.editnow.configuration.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class UUIDAuthentication implements Authentication {

  private final String userUUID;

  private Collection<GrantedAuthority> collection = new ArrayList<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return collection;
  }

  @Override
  public Object getCredentials() {
    return userUUID;
  }

  @Override
  public Object getDetails() {
    return userUUID;
  }

  @Override
  public Object getPrincipal() {
    return userUUID;
  }

  @Override
  public boolean isAuthenticated() {
    return userUUID != null;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
  }

  @Override
  public String getName() {
    return userUUID;
  }
}
