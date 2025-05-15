package ar.com.acme.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "ar.com.acme")
@EnableJpaRepositories(basePackages = "ar.com.acme.model")
@EntityScan("ar.com.acme.model")
public class AcmeApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(AcmeApplication.class, args);
	}
}
