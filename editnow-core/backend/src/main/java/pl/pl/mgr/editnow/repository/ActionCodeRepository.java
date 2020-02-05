package pl.pl.mgr.editnow.repository;

import org.springframework.data.repository.CrudRepository;
import pl.pl.mgr.editnow.domain.configuration.ActionCode;
import pl.pl.mgr.editnow.dto.action.ActionType;

public interface ActionCodeRepository extends CrudRepository<ActionCode, Long> {

  ActionCode findActionCodeByActionType(ActionType actionType);
  boolean existsActionCodeByActionType(ActionType actionType);
}
