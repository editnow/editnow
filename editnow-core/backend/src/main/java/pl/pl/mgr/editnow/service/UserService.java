package pl.pl.mgr.editnow.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pl.mgr.editnow.domain.User;
import pl.pl.mgr.editnow.repository.UserRepository;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

  private final UserRepository userRepository;

  public String generateUUID() {
    String generatedUUID = UUID.randomUUID().toString();

    User newUser = new User();
    newUser.setUuid(generatedUUID);
    userRepository.save(newUser);

    return generatedUUID;
  }

  public String getUserUUIDFromContext() {
    return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

  public User getUserFromContext() {
    String userUUIDFromContext = getUserUUIDFromContext();

    return userRepository.findByUuid(userUUIDFromContext);
  }

  public void addToDatabaseIfNotExist(String uuid) {
    User userFromDb = userRepository.findByUuid(uuid);

    if (userFromDb == null) {
      User user = new User();
      user.setUuid(uuid);

      try {
        userRepository.save(user);
        log.info( "Added to database user: " + uuid);
      } catch (Exception exc) {
        log.warn("User: " + uuid + " exist in database");
      }
    }
  }

}
