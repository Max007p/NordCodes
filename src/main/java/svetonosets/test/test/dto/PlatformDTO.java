package svetonosets.test.test.dto;

import lombok.Data;

@Data
public class PlatformDTO {
    private String platformName;
    private Double coef;

    public PlatformDTO(String platformName, Double coef) {
        this.platformName = platformName;
        this.coef = coef * 100;
    }
}
