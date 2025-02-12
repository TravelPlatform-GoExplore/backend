package org.anaservinovska.travelplatform.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Development");

        Contact myContact = new Contact();
        myContact.setName("Ana Servinovska");
        myContact.setEmail("ana.servinovska@student.finki.ukim.mk");

        Info information = new Info()
                .title("GoExplore System API")
                .version("1.0")
                .description("This API exposes endpoints to GoExplore.")
                .contact(myContact);
        return new OpenAPI().info(information).servers(List.of(server));
    }
}