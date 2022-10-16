package pl.trytek.easytrip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@SpringBootApplication
@ComponentScan(basePackages = "pl.trytek.easytrip.*")//, includeFilters = @ComponentScan.Filter({ControllerAdvice.class}))
@EntityScan(basePackages = {"pl.trytek.easytrip.data.domain"})
@EnableJpaRepositories(value = "pl.trytek.easytrip.data.repository")
public class EasyTripApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(EasyTripApplication.class, args);
	}
}
