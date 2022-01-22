package svetonosets.test.test.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "url_visit")
public class UrlVisitsInfo {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="url_id", nullable = false)
    private Url url;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date visitedDate;

    @Column(nullable = false)
    private String platform;

    @Column(nullable = false)
    private String clientsIp;

    public UrlVisitsInfo(Url url, Date visitedDate, String platform, String clientsIp) {
        this.url = url;
        this.visitedDate = visitedDate;
        this.platform = platform;
        this.clientsIp = clientsIp;
    }
}
