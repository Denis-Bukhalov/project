package whim.project.lecturer;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import whim.project.subjects.Subject;
import whim.project.utils.ErrorResponse;

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

	@Operation(description = "Получить предметы которые преподает преподаватель", tags = "Предмет")
	@ApiResponse(responseCode = "200", description = "ok")
	@ApiResponse(responseCode = "404", description = "lecturer not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	@RequestMapping(path = "lecturers/{id}/subjects", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Set<Subject> getSubjectsOfLecturer(@PathVariable("id") long id) {
		var lecturer = lecturerService.findById(id);
		if (lecturer.isPresent())
			return lecturer.get().getSubjects();
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("lecturer with id=%s not found", id));
	}
}
