package pl.pl.mgr.editnow.domain;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="action_chains")
@Getter
@Setter
public class ActionChain {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @OneToMany(fetch = FetchType.EAGER)
  @JoinColumn(name = "actionChain_id")
  @OrderBy
  private List<Action> actions;

}
