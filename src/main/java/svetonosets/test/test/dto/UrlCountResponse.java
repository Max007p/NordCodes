package svetonosets.test.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlCountResponse {
    private String longUrl;
    private Long count;
}
