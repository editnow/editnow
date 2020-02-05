package pl.pl.mgr.editnow.domain.configuration;

import lombok.Getter;
import lombok.Setter;
import pl.pl.mgr.editnow.dto.action.ActionType;
import pl.pl.mgr.editnow.dto.configuration.ActionToolCategory;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="action_tools")
@Getter
@Setter
public class ActionTool {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Enumerated(EnumType.STRING)
  private ActionType actionType;

  @Enumerated(EnumType.STRING)
  private ActionToolCategory actionToolCategory;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "actionTool_id")
  private List<ParameterInfo> parameterInfos;

  private boolean disabled;

}
