package whim.project.marks;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import whim.project.students.Student;
import whim.project.students.StudentService;
import whim.project.task.Task;
import whim.project.task.TaskService;
import whim.project.utils.ErrorResponse;

@Data
class MarkDTO {
	@NotNull
	private Long studentId;
	@NotNull
	private Long taskId;
	private Double value = 0.0;
}

@Data
class MarkDTOupdate {
	@NotNull
	private Double value;
}

@RestController
@RequestMapping("/api")
@Tag(name = "Оценка")
public class MarkController {

	@Autowired
	private MarkService markService;

	@Autowired
	private StudentService studentService;

	@Autowired
	private TaskService taskService;

	@RequestMapping(path = "marks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "получить все оценки")
	public List<Mark> getAll() {
		return markService.getAllMarks();
	}

	@RequestMapping(path = "marks", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "Создать оценку")
	@ApiResponse(responseCode = "200", description = "ok")
	@ApiResponse(responseCode = "404", description = "task or student with id in request body not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	@ApiResponse(responseCode = "404", description = "оценка этого студента по этому заданию уже существует", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<Mark> createMark(@Valid @RequestBody() MarkDTO markDTO) {
		Optional<Student> optStudent = studentService.getStudentById(markDTO.getStudentId());
		Optional<Task> optTask = taskService.getTaskById(markDTO.getTaskId());

		if (optStudent.isPresent() && optTask.isPresent()) {
			var task = optTask.get();
			var student = optStudent.get();
			if (!markService.MarkExistByStudentIdAndTaskId(student.getId(), task.getId())) {
				return new ResponseEntity<>(markService.saveMark(new Mark(
						student.getId(), task.getId(), markDTO.getValue())), HttpStatus.OK);
			} else {
				throw new ResponseStatusException(HttpStatus.CONFLICT,
						String.format("mark of this student and task already exist", markDTO.getTaskId()));
			}
		} else {
			if (optTask.isEmpty())
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,
						String.format("task with id=%s not found", markDTO.getTaskId()));
			if (optStudent.isEmpty())
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,
						String.format("student with id=%s not found", markDTO.getStudentId()));
		}
		return null; // unreachable
	}

	@RequestMapping(path = "marks/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "изменить существующую оценку")
	@ApiResponse(responseCode = "200", description = "ok")
	@ApiResponse(responseCode = "404", description = "mark not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<Mark> updateMark(@Valid @RequestBody MarkDTOupdate markDTO, @PathVariable("id") long id) {
		var OMark = markService.getMarkById(id);
		if (OMark.isPresent()) {
			var mark = OMark.get();
			mark.setValue(markDTO.getValue());
			return new ResponseEntity<>(markService.saveMark(mark), HttpStatus.OK);
		} else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					String.format("mark with id=%s not found", id));
	}

	@RequestMapping(path = "marks/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "удалить оценку")
	@ApiResponse(responseCode = "200", description = "ok")
	@ApiResponse(responseCode = "404", description = "mark not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<Mark> deleteMark(@PathVariable("id") long id) {
		List<Mark> marks = markService.removeMark(id);
		if (!marks.isEmpty()) {
			return new ResponseEntity<>(marks.get(0), HttpStatus.OK);
		} else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					String.format("mark with id=%s not found", id));
	}

	@RequestMapping(path = "marks/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "получить оценку по идентификатору")
	@ApiResponse(responseCode = "200", description = "ok")
	@ApiResponse(responseCode = "404", description = "mark not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<Mark> getTaskById(@PathVariable("id") Long id) {
		var optionalMark = markService.getMarkById(id);

		if (optionalMark.isPresent()) {
			return new ResponseEntity<>(
					optionalMark.get(),
					HttpStatus.OK);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("mark with id=%s not found", id));
		}
	}
}
