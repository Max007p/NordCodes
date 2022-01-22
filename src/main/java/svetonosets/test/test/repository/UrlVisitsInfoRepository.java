package svetonosets.test.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import svetonosets.test.test.dto.PlatformDTO;
import svetonosets.test.test.entity.Url;
import svetonosets.test.test.entity.UrlVisitsInfo;

import java.util.List;

@Repository
public interface UrlVisitsInfoRepository extends JpaRepository<UrlVisitsInfo, Long> {

    @Query(value = "SELECT NEW svetonosets.test.test.dto.PlatformDTO(a.platform, cast((COUNT(a.platform) / (SELECT COUNT(b) FROM UrlVisitsInfo b WHERE b.url.id = :urlId)) as java.lang.Double)) " +
            "FROM UrlVisitsInfo a WHERE a.url.id = :urlId " +
            "GROUP BY a.platform")
    List<PlatformDTO> getPlatformRelation(long urlId);

    Boolean existsByUrlId(Long urlId);

    Long countAllByUrl(Url url);
}
