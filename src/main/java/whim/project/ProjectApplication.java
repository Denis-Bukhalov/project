package whim.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;

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

}
