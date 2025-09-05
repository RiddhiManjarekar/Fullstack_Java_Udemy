package org.studyeasy.SprinRestDemo.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@Configuration
@OpenAPIDefinition(
    info=@Info(
        title="Demo API",
        version="Versions 1.0",
        contact=@Contact(
            name="StudyEasy",email="blockchain122@gmail.com",url="https://studyeasy.org"
        ),
        license=@License(
            name="Apache 2.0",url="https://www.apache.org/license/LICENSE-2.0"
        ),
        termsOfService="https://studyeasy.org/",
        description="Spring Boot Restful API Demo by Riddhi"
    )
)
public class SwaggerConfig {


    
}
