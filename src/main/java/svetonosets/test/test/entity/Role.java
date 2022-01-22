package svetonosets.test.test.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import svetonosets.test.test.enums.ERole;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;
}
