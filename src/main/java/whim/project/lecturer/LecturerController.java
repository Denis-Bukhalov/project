package whim.project.lecturer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Преподаватель")
@RestController
@RequestMapping("/api")
public class LecturerController {

	@Autowired
	private LecturerService lecturerService;

	@Operation(description = "Получить всех преподавателей")
	@RequestMapping(path = "lecturers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Lecturer> getAll() {
		return lecturerService.getAll();
	}
}
