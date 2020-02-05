package pl.pl.mgr.editnow.domain;

import lombok.Getter;
import lombok.Setter;
import pl.pl.mgr.editnow.dto.ImageType;
import javax.persistence.*;

@Entity
@Table(name="images")
@Getter
@Setter
public class Image {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String name;

  @Enumerated(EnumType.STRING)
  private ImageType type;

}
