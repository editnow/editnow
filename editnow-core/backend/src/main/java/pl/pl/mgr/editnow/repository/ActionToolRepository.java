package pl.pl.mgr.editnow.repository;

import org.springframework.data.repository.CrudRepository;
import pl.pl.mgr.editnow.domain.configuration.ActionTool;
import pl.pl.mgr.editnow.dto.action.ActionType;
import pl.pl.mgr.editnow.dto.configuration.ActionToolCategory;

import java.util.List;

public interface ActionToolRepository extends CrudRepository<ActionTool, Long> {

  List<ActionTool> findActionToolsByActionToolCategory(ActionToolCategory actionToolCategory);
  boolean existsActionToolByActionTypeAndActionToolCategory(ActionType actionType, ActionToolCategory actionToolCategory);

}
