package whim.project.students;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "Студент")
public class StudentController {

	@Autowired
	private StudentService studentService;

	@Operation(description = "Получить всех студентов")
	@RequestMapping(path = "students", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Student> getAll() {
		return studentService.getAll();
	}
}
