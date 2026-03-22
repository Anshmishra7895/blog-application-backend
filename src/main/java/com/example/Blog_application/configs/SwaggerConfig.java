package com.example.Blog_application.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//This is the configuration file of swagger api where we can customize the Swagger UI
//first we need to make the bean of OpenApi and there we only need to return the new OpenApi.info(apiInfo()), if we are not authorize Swagger Api to use JWT
//but if we want to use the JWT feature then we have to add the components and make the object of Components() to access addSecuritySchemes where we can pass the name, type, schemes and bearer format
// and also atl-ast add addSecurityItems

//Now it's time to make the apiInfo where we can provide the title, description, version, contact details, license,etc

public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .info(apiInfo())
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                )
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName));
    }

    private Info apiInfo() {
        return new Info()
                .title("Blog Application API")
                .description("This is the API documentation of Blog Application")
                .version("1.0.0")
                .contact(new Contact()
                        .name("Ansh Mishra")
                        .email("ansh2003mishra@gmail.com"))
                .license(new License()
                        .name("License")
                        .url("http://spingdoc.org"));
    }

}
