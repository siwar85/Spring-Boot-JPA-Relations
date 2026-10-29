package tn.esprit.autoloc;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "AutoLoc API",
                version = "2.0",
                description = "API REST de gestion de location de véhicules.",
                contact = @Contact(
                        name = "Équipe AutoLoc",
                        email = "VOTRE.ADRESSE@outlook.com",
                        url = "https://github.com/siwar85"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "Dépôt Git du projet",
                url = "https://github.com/siwar85"
        )
)
public class AutolocApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutolocApiApplication.class, args);
    }

}