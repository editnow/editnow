package pl.pl.mgr.editnow.repository;

import org.springframework.data.repository.CrudRepository;
import pl.pl.mgr.editnow.domain.Action;

public interface ActionRepository extends CrudRepository<Action, Long> {

  Action findById(long actionId);
}
