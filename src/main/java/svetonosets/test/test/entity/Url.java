package svetonosets.test.test.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "url")
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy="url", cascade = CascadeType.ALL)
    Set<UrlVisitsInfo> urlVisits = new HashSet();

    @Column(nullable = false)
    private String longUrl;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiresDate;
}
