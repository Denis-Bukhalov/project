package whim.project.group;

import java.util.HashSet;
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
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import whim.project.students.Student;
import whim.project.subjects.Subject;
import whim.project.utils.ErrorResponse;

@RestController
@RequestMapping("/api")
@Tag(name = "Группа")
public class GroupController {

	@Autowired
	private GroupService groupService;

	@Operation(description = "получить информацию о всех группах")
	@RequestMapping(path = "/groups", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public List<Group> getAll() {
		return groupService.getAll();
	}

	@Operation(description = "Получить группу по id")
	@ApiResponse(responseCode = "200", description = "ok")
	@ApiResponse(responseCode = "404", description = "group not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	@RequestMapping(path = "/groups/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<Group> getByID(@PathVariable("id") long id) {
		var group = groupService.findById(id);
		if (!group.isEmpty())
			return new ResponseEntity<Group>(group.get(), HttpStatus.OK);
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("group with id=%s not found", id));
	}

	@Operation(description = "Получить всех студентов из группы", tags = "Студент")
	@ApiResponse(responseCode = "200", description = "ok")
	@ApiResponse(responseCode = "404", description = "group not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	@RequestMapping(path = "/groups/{id}/students", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Set<Student> getAllStudentsFromGroup(@PathVariable("id") long id) {
		var group = groupService.findById(id);
		if (!group.isEmpty())
			return new HashSet<Student>(group.get().getStudents());
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("group with id=%s not found", id));
	}

	@Operation(description = "Получить все предметы группы студентов", tags = "Предмет")
	@ApiResponse(responseCode = "200", description = "ok")
	@ApiResponse(responseCode = "404", description = "group not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	@RequestMapping(path = "/groups/{id}/subjects", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Set<Subject> getAllSubjectsOfGroup(@PathVariable("id") long id) {
		var group = groupService.findById(id);
		if (!group.isEmpty())
			return new HashSet<>(group.get().getSubjects());
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("group with id=%s not found", id));
	}
}
