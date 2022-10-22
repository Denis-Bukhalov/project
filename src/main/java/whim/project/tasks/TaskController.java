package whim.project.tasks;

import java.util.HashSet;
import java.util.List;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import whim.project.subjects.SubjectService;
import whim.project.utils.ErrorResponse;

@Data
class TaskDTO {
	private String description;
	private Long subjectId;
}

@Data
class TaskDTOupdate {
	private String description;
}

// TODO: multithreading: LOL HEH MDA

// TODO: add documentation

@Tag(name = "Задание")
@RestController
@RequestMapping("/api")
public class TaskController {

	@Autowired
	private TaskService taskService;
	@Autowired
	private SubjectService subjectService;

	@RequestMapping(path = "tasks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "получить все задания")
	public List<Task> getAll() {
		return taskService.getAll();
	}

	@RequestMapping(path = "tasks", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "создать задание")
	@ApiResponse(responseCode = "200", description = "ok")
	@ApiResponse(responseCode = "404", description = "subject with id in request body not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<Task> createTask(@RequestBody TaskDTO task) {
		var subject_ = subjectService.findById(task.getSubjectId());
		if (subject_.isPresent()) {
			return new ResponseEntity<>(taskService.saveTask(new Task(task.getDescription(), task.getSubjectId())),
					HttpStatus.OK);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					String.format("subject with id=%s not found", task.getSubjectId()));
		}
	}

	@RequestMapping(path = "tasks/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "обновляет существующее задание")
	@ApiResponse(responseCode = "200", description = "ok")
	@ApiResponse(responseCode = "404", description = "task not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<Task> updateTask(@PathVariable("id") Long id, @RequestBody TaskDTOupdate task) {
		var optionalTask = taskService.findById(id);

		if (optionalTask.isPresent()) {
			var task_ = optionalTask.get();
			return new ResponseEntity<>(
					taskService.saveTask(
							new Task(task_.getId(), task.getDescription(), task_.getSubjectId(), new HashSet<>())),
					HttpStatus.OK);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("task with id=%s not found", id));
		}
	}

	@RequestMapping(path = "tasks/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "удаляет задание")
	@ApiResponse(responseCode = "204", description = "всё удачно удалено")
	@ApiResponse(responseCode = "404", description = "task not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<Void> createTask(@PathParam("id") long id) {
		var rows = taskService.deleteTask(id);
		if (rows != 0) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("task with id=%s not found", id));
		}
	}

}
