package whim.project.tasks;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import whim.project.marks.Mark;

@Entity
@Table(name = "tasks")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Модель задания")
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "уникальный идентификатор задания", example = "1")
	private Long id;

	@Column(name = "description", nullable = true, length = 255)
	@Schema(description = "описание задания", nullable = true, maxLength = 255)
	private String description;

	@Column(name = "subject_id", nullable = false, updatable = false, insertable = true)
	@Schema(description = "уникальный идентификатор предмета соответствующего задания", nullable = false)
	private Long subjectId;

	@OneToMany(orphanRemoval = true, targetEntity = Mark.class)
	@JoinColumn(name = "task_id")
	@Schema(hidden = true)
	@JsonIgnore
	private Set<Mark> marks = new HashSet<>();

	public Task(String description, Long subjectId) {
		this.description = description;
		this.subjectId = subjectId;
	}

}
