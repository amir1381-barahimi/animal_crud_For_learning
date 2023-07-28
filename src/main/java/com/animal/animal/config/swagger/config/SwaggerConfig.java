package com.animal.animal.config.swagger.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${amir.openapi.dev-url}")
    private String devUrl;


    @Bean
    public OpenAPI mySwaggerConfig(){
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("animal APIs");

        Contact contact = new Contact();
        contact.setName("amirmohsen barahimi");
        contact.setEmail("amirmohsenbarahimi1381@gmail.com");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("APIs for Animal")
                .version("1.0")
                .contact(contact)
                .license(mitLicense)
                .description("animal APIs");

        return new OpenAPI().servers(List.of(devServer)).info(info);
    }
}
