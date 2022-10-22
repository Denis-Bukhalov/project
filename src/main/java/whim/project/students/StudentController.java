package whim.project.students;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import whim.project.utils.ErrorResponse;

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

	@Operation(description = "Получить студента по id")
	@ApiResponse(responseCode = "200", description = "ok")
	@ApiResponse(responseCode = "404", description = "student not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	@RequestMapping(path = "/students/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<Student> getByID(@PathVariable("id") long id) {
		var optionalStudent = studentService.getStudentById(id);
		if (optionalStudent.isPresent())
			return new ResponseEntity<>(optionalStudent.get(), HttpStatus.OK);
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("student with id=%s not found", id));
	}
}
