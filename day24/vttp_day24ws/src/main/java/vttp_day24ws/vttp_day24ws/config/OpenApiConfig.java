package vttp_day24ws.vttp_day24ws.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI OpenAPI(){
        return new OpenAPI().info(
            new Info()
            .title("PAF day24")
            .description("testing openAPI")
            .version("1.0")
        );
    }

    //this creates a li
}
