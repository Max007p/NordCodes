package svetonosets.test.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import svetonosets.test.test.config.OpenAPIConfig;
import svetonosets.test.test.config.WebSecurityConfig;

@ComponentScan(value = {
        "svetonosets.test.test.service",
        "svetonosets.test.test.controller",
        "svetonosets.test.test.security"
})
@EntityScan("svetonosets.test.test.entity")
@EnableJpaRepositories("svetonosets.test.test.repository")
@Import({OpenAPIConfig.class, WebSecurityConfig.class})
@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

}
