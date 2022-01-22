package svetonosets.test.test.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlIdRequest {
    @Schema(description = "Url id.", required = true)
    private long urlId;
}
