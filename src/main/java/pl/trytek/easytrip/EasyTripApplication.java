package pl.trytek.easytrip;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@SpringBootApplication
@ComponentScan(basePackages = "pl.trytek.easytrip.**")//, includeFilters = @ComponentScan.Filter({ControllerAdvice.class}))
@EntityScan(basePackages = {"pl.trytek.easytrip.data.domain"})
@EnableJpaRepositories(value = "pl.trytek.easytrip.data.repository")
@OpenAPIDefinition(info =
@Info(title = "Easy-Trip Rest API", version = "1.0", description = "Easy-Trip Rest API desc",
		contact = @Contact(url = "https://github.com/KarolTrytek", name = "Easy-Trip", email = "k_trytek@outlook.com")),
		security = @SecurityRequirement(name = "token"),
		servers = @Server(url = "http://127.0.0.1:7060/easy-trip", description = "Easy-Trip server"))
//@EnableSwagger2
public class EasyTripApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(EasyTripApplication.class, args);
	}

}
