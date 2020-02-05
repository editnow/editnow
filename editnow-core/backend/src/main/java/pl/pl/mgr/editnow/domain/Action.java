package pl.pl.mgr.editnow.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import pl.pl.mgr.editnow.dto.action.ActionType;
import pl.pl.mgr.editnow.dto.action.ActionStatus;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="actions")
@Getter
@Setter
public class Action {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Enumerated(EnumType.STRING)
  private ActionType actionType;

  @OneToOne
  private Image inputImage;

  @OneToOne
  private Image outputImage;

  @Enumerated(EnumType.STRING)
  private ActionStatus status;

  @ElementCollection
  private List<Parameter> parameters;

  @ManyToOne
  @JoinColumn
  @JsonIgnore
  private User user;

}
