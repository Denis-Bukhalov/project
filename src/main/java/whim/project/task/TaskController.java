package whim.project.task;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
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
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.NoArgsConstructor;
import whim.project.marks.Mark;
import whim.project.task_groups.TaskGroupService;
import whim.project.utils.ErrorResponse;

@Data
@NoArgsConstructor
class TaskDTO {
	@NotNull
	@Size(max = 64)
	private String title;
	@NotNull
	@Positive
	private Integer value;
	@NotNull
	@Positive
	private Integer firstValue;
	@NotNull
	private Long taskGroup;
}

@Data
@NoArgsConstructor
class TaskDTOupdate {
	@Size(max = 64)
	private String title;
	@Positive
	private Integer value;
	@Positive
	private Integer firstValue;
}

@RestController
@RequestMapping("/api")
@Tag(name = "Задание")
public class TaskController {

	@Autowired
	private TaskService taskService;
	@Autowired
	private TaskGroupService taskGroupService;

	@RequestMapping(path = "tasks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "получить все задания")
	public List<Task> getAll() {
		return taskService.getAllTasks();
	}

	@Operation(description = "получить задание по id")
	@ApiResponse(responseCode = "404", description = "task not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	@RequestMapping(path = "tasks/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Task> getTaskByID(@PathVariable("id") Long id) {
		var optTask = taskService.getTaskById(id);

		if (optTask.isPresent()) {
			return new ResponseEntity<>(
					optTask.get(),
					HttpStatus.OK);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					String.format("task  with id=%s not found", id));
		}
	}

	@Operation(description = "получить все оценки по заданию")
	@ApiResponse(responseCode = "404", description = "task not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	@RequestMapping(path = "tasks/{id}/marks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Set<Mark>> getMarkFromTaskByID(@PathVariable("id") Long id) {
		var optTask = taskService.getTaskById(id);

		if (optTask.isPresent()) {
			return new ResponseEntity<>(
					optTask.get().getMarks(),
					HttpStatus.OK);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					String.format("task  with id=%s not found", id));
		}
	}

	@RequestMapping(path = "tasks/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Task> removeTask(@PathVariable("id") Long id) {
		var tasks = taskService.removeById(id);

		if (!tasks.isEmpty()) {
			return new ResponseEntity<>(tasks.get(0), HttpStatus.OK);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					String.format("task  with id=%s not found", id));
		}
	}

	@RequestMapping(path = "tasks", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "создать задание")
	@ApiResponse(responseCode = "200", description = "ok")
	@ApiResponse(responseCode = "404", description = "subject with id in request body not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<Task> createTask(@Valid @RequestBody TaskDTO taskDTO) {
		var optTaskGroup = taskGroupService.findById(taskDTO.getTaskGroup());
		if (optTaskGroup.isPresent()) {
			return new ResponseEntity<>(
					taskService
							.saveTask(new Task(taskDTO.getTitle(), taskDTO.getValue(),
									taskDTO.getFirstValue(), optTaskGroup.get().getId())),
					HttpStatus.OK);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					String.format("subject with id=%s not found", taskDTO.getTaskGroup()));
		}
	}

	@RequestMapping(path = "tasks/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "обновляет существующее задание")
	@ApiResponse(responseCode = "200", description = "ok")
	@ApiResponse(responseCode = "404", description = "task not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<Task> updateTaskGroup(@PathVariable("id") Long id,
			@Valid @RequestBody TaskDTOupdate task) {
		var optTask = taskService.getTaskById(id);

		if (optTask.isPresent()) {
			var task_ = optTask.get();
			var title = task.getTitle() == null ? task_.getTitle() : task.getTitle();
			var value = task.getValue() == null ? task_.getValue() : task.getValue();
			var first_value = task.getFirstValue() == null ? task_.getFirstValue() : task.getFirstValue();

			return new ResponseEntity<>(
					taskService.saveTask(
							new Task(task_.getId(), title, value, first_value, task_.getTaskGroupId(),
									new HashSet<>())),
					HttpStatus.OK);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					String.format("task with id=%s not found", id));
		}
	}
}
