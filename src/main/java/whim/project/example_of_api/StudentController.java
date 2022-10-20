package whim.project.example_of_api;
// package whim.project.Student;

// import java.util.ArrayList;
// import java.util.List;

// import org.springframework.http.MediaType;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;
// import org.springframework.web.bind.annotation.RestController;

// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.tags.Tag;

// @Tag(name = "Студент")
// @RestController
// @RequestMapping("/students")
// public class StudentController {

// @Operation(description = "Получить всех студентов")
// @RequestMapping(method = RequestMethod.GET, produces =
// MediaType.APPLICATION_JSON_VALUE)
// public List<Student> getAll() {
// return new ArrayList<Student>();
// }

// @Operation(description = "Получить студента по идентификатору")
// @RequestMapping(path = "/id", method = RequestMethod.GET, produces =
// MediaType.APPLICATION_JSON_VALUE)
// public Student get() {
// return new Student(0, "denis", "bukhalov");
// }
// }
