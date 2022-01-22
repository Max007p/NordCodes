package svetonosets.test.test.dto;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@OpenAPIDefinition(info = @Info(description = "Long url dto."))
public class UrlLongRequest {
    @Schema(description = "Url to be shorten", required = true)
    private String longUrl;
    @Schema(description = "Expire datetime of url")
    private Date expiresDate;
}
