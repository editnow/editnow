package pl.pl.mgr.editnow.domain.configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pl.mgr.editnow.dto.configuration.ParameterType;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="parameter_infos")
public class ParameterInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String name;

  @Enumerated(EnumType.STRING)
  private ParameterType parameterType;

  private String tips;

  @ElementCollection
  private List<ParameterInfoOption> parameterInfoOptions;

  public ParameterInfo(String name, ParameterType parameterType, String tips) {
    this.name = name;
    this.parameterType = parameterType;
    this.tips = tips;
  }

  public ParameterInfo(String name, ParameterType parameterType, String tips, List<ParameterInfoOption> parameterInfoOptions) {
    this.name = name;
    this.parameterType = parameterType;
    this.tips = tips;
    this.parameterInfoOptions = parameterInfoOptions;
  }

}
