package pl.pl.mgr.editnow.domain;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name="users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String uuid;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "actionChain_id")
    private ActionChain actionChain;

}
