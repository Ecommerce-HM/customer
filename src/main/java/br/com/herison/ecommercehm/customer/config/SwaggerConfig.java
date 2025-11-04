package br.com.herison.ecommercehm.customer.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("EcommerceHM - Customer API")
                        .description("Documentation for the EcommerceHM system's Customer and Address API.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Herison Maciel")
                                .email("herisson2maciel@hotmail.com")
                                .url("https://github.com/Ecommerce-HM/customer"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html"))
                );
    }
}