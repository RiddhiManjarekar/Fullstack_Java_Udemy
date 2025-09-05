package org.studyeasy.SprinRestDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.studyeasy.SprinRestDemo.config.RsaKeyProperties;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SpringBootApplication
@SecurityScheme(name="studyeasy-demo-api",scheme="bearer",type=SecuritySchemeType.HTTP,in=SecuritySchemeIn.HEADER)
@EnableConfigurationProperties(RsaKeyProperties.class)
public class SprinRestDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SprinRestDemoApplication.class, args);
	}

}
