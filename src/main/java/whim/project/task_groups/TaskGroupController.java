package whim.project.task_groups;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.NoArgsConstructor;
import whim.project.subjects.SubjectService;
import whim.project.task.Task;
import whim.project.utils.ErrorResponse;

@Data
@NoArgsConstructor
class TaskGroupDTO {
	@NotNull
	@Size(max = 255)
	private String title;
	@NotNull
	private Long subjectId;
	@NotNull
	@Pattern(regexp = "^#[0-9a-fA-F]{6}$")
	private String bgColor;
}

@Data
class TaskGroupDTOupdate {
	private String title;
	@Pattern(regexp = "^#[0-9a-fA-F]{6}$")
	private String bgColor;
}

// TODO: multithreading: LOL HEH MDA

// TODO: add documentation

@Tag(name = "Группа заданий")
@RestController
@RequestMapping("/api")
public class TaskGroupController {

	@Autowired
	private TaskGroupService taskGroupService;
	@Autowired
	private SubjectService subjectService;

	@RequestMapping(path = "task_groups", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "получить все задания")
	public List<TaskGroup> getAll() {
		return taskGroupService.getAll();
	}

	@RequestMapping(path = "task_groups", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "создать задание")
	@ApiResponse(responseCode = "200", description = "ok")
	@ApiResponse(responseCode = "404", description = "subject with id in request body not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<TaskGroup> createTask(@Valid @RequestBody TaskGroupDTO groupTask) {
		var subject_ = subjectService.findById(groupTask.getSubjectId());
		if (subject_.isPresent()) {
			return new ResponseEntity<>(
					taskGroupService
							.saveTaskGroup(new TaskGroup(groupTask.getTitle(), groupTask.getBgColor(),
									groupTask.getSubjectId())),
					HttpStatus.OK);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					String.format("subject with id=%s not found", groupTask.getSubjectId()));
		}
	}

	@RequestMapping(path = "task_groups/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "обновляет существующее задание")
	@ApiResponse(responseCode = "200", description = "ok")
	@ApiResponse(responseCode = "404", description = "task not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<TaskGroup> updateTaskGroup(@PathVariable("id") Long id,
			@Valid @RequestBody TaskGroupDTOupdate groupTask) {
		var optionalTaskGroup = taskGroupService.findById(id);

		if (optionalTaskGroup.isPresent()) {
			var taskGroup_ = optionalTaskGroup.get();
			var title = groupTask.getTitle() == null ? taskGroup_.getTitle() : groupTask.getTitle();
			var bgColor = groupTask.getBgColor() == null ? taskGroup_.getBgColor() : groupTask.getBgColor();
			return new ResponseEntity<>(
					taskGroupService.saveTaskGroup(
							new TaskGroup(taskGroup_.getId(), title, bgColor,
									taskGroup_.getSubjectId(),
									new HashSet<>())),
					HttpStatus.OK);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					String.format("task group with id=%s not found", id));
		}
	}

	@RequestMapping(path = "task_groups/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "удаляет групу заданий")
	@ApiResponse(responseCode = "404", description = "task not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<TaskGroup> removeTaskGroup(@PathParam("id") long id) {
		List<TaskGroup> taskGroups = taskGroupService.removeById(id);

		if (!taskGroups.isEmpty()) {
			return new ResponseEntity<>(
					taskGroups.get(0),
					HttpStatus.OK);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					String.format("task group with id=%s not found", id));
		}
	}

	@RequestMapping(path = "task_groups/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "получить задание по идентификатору")
	@ApiResponse(responseCode = "200", description = "ok")
	@ApiResponse(responseCode = "404", description = "task not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<TaskGroup> getTaskGroupById(@PathVariable("id") Long id) {
		var optionalTaskGroup = taskGroupService.findById(id);

		if (optionalTaskGroup.isPresent()) {
			return new ResponseEntity<>(
					optionalTaskGroup.get(),
					HttpStatus.OK);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					String.format("task group with id=%s not found", id));
		}
	}

	@RequestMapping(path = "task_groups/{id}/tasks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "получить все задания из группы заданий")
	@ApiResponse(responseCode = "200", description = "ok")
	@ApiResponse(responseCode = "404", description = "task not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<Set<Task>> getTaskInTaskGroup(@PathVariable("id") Long id) {
		var optionalTask = taskGroupService.findById(id);

		if (optionalTask.isPresent()) {
			return new ResponseEntity<>(
					optionalTask.get().getTasks(),
					HttpStatus.OK);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					String.format("task group with id=%s not found", id));
		}
	}

}
