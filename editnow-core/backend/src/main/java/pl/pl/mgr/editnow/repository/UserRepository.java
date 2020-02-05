package pl.pl.mgr.editnow.repository;

import pl.pl.mgr.editnow.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

  User findByUuid(String uuid);

}
