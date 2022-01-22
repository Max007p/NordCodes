package svetonosets.test.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlStatisticResponse {
    private String longUrl;
    private List<PlatformDTO> platformsRelation;
}