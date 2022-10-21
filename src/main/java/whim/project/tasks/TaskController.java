package whim.project.tasks;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
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
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import whim.project.subjects.Subject;
import whim.project.subjects.SubjectService;

@Data
class TaskDTO {
	private String description;
	private Long subjectId;
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
	public ResponseEntity<Task> createTask(@RequestBody TaskDTO task) {
		var subject_ = subjectService.findById(task.getSubjectId());
		if (subject_.isPresent()) {
			// var subject = subject_.get();
			return new ResponseEntity<>(taskService.saveTask(new Task(task.getDescription(), task.getSubjectId())),
					HttpStatus.OK);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					String.format("subject with id=%s not found", task.getSubjectId()));
		}
	}

	@RequestMapping(path = "tasks/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Task> updateTask(@PathVariable("id") Long id, @RequestBody TaskDTO task) {
		var optionalTask = taskService.findById(id);

		if (optionalTask.isPresent()) {
			var task_ = optionalTask.get();
			return new ResponseEntity<>(
					taskService.saveTask(new Task(task_.getId(), task.getDescription(), task_.getSubjectId())),
					HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(path = "tasks/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> createTask(@PathParam("id") long id) {
		var rows = taskService.deleteTask(id);
		if (rows != 0) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("task with id=%s not found", id));
		}
	}

}
