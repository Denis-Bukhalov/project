package whim.project.group;

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
import whim.project.students.Student;

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
	@RequestMapping(path = "/groups/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<Group> getByID(@PathVariable("id") long id) {
		var group = groupService.findById(id);
		if (!group.isEmpty())
			return new ResponseEntity<Group>(group.get(), HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@Operation(description = "Получить всех студентов из группы")
	@ApiResponse(responseCode = "200", description = "ok")
	@ApiResponse(responseCode = "404", description = "group not found")
	@RequestMapping(path = "/groups/{id}/students", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<Set<Student>> getAllStudentsFromGroup(@PathVariable("id") long id) {
		var group = groupService.findById(id);
		if (!group.isEmpty())
			return new ResponseEntity<Set<Student>>(group.get().getStudents(), HttpStatus.OK);
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("group with id=%s not found", id));
	}

}
