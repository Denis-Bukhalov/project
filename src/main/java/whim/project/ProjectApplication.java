package whim.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@SpringBootApplication
public class ProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

	@Bean
	public OpenAPI springBootOpenAPI() {
		return new OpenAPI()
				.info(
						new Info()
								.title("Университетский проект")
								.description("Таблица успеваемости"));
	}

	@GetMapping("/")
	public String home() {
		return "index";
	}

	@GetMapping("/student")
	@PreAuthorize("hasRole('STUDENT')")
	public String student() {
		return "STUDENT REEEEEEEEEEEEEE";
	}

	@GetMapping("/lecturer")
	@PreAuthorize("hasRole('LECTURER')")
	public String lecturer() {
		return "LECTURER REEEEEEEEEEEEEE";
	}

	@GetMapping("/me")
	public Object getMe() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}

}
