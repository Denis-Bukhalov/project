package whim.project.subjects;

import java.util.List;
import java.util.Set;

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
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import whim.project.lecturer.Lecturer;
import whim.project.task_groups.TaskGroup;
import whim.project.utils.ErrorResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@Tag(name = "Предмет")
@RestController
@RequestMapping("/api")
public class SubjectController {

	@Autowired
	private SubjectService subjectService;

	@Operation(description = "Получить все предметы")
	@RequestMapping(path = "subjects", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Subject> getAll() {
		return subjectService.getAll();
	}

	@Operation(description = "Получить всех преподавателей кто перепадает данный предмет", tags = "Преподаватель")
	@ApiResponse(responseCode = "200", description = "ok")
	@ApiResponse(responseCode = "404", description = "subject not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	@RequestMapping(path = "subjects/{id}/lecturers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Set<Lecturer> getLecturersOfSubject(@PathVariable("id") long id) {
		var lecturer = subjectService.findById(id);
		if (lecturer.isPresent())
			return lecturer.get().getLecturers();
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("subject with id=%s not found", id));
	}

	@Operation(description = "Получить все группы заданий данного предмета", tags = "Группа заданий")
	@ApiResponse(responseCode = "200", description = "ok")
	@ApiResponse(responseCode = "404", description = "subject not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	@RequestMapping(path = "subjects/{id}/tasks_groups", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Set<TaskGroup> getTasksOfSubject(@PathVariable("id") long id) {
		var subject = subjectService.findById(id);
		if (subject.isPresent())
			return subject.get().getTaskGroups();
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("subject with id=%s not found", id));
	}

	@Operation(description = "Получить предмет по id")
	@ApiResponse(responseCode = "200", description = "ok")
	@ApiResponse(responseCode = "404", description = "subject not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	@RequestMapping(path = "/subjects/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<Subject> getByID(@PathVariable("id") long id) {
		var optionalSubject = subjectService.findById(id);
		if (optionalSubject.isPresent())
			return new ResponseEntity<>(optionalSubject.get(), HttpStatus.OK);
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("subject with id=%s not found", id));
	}
}
