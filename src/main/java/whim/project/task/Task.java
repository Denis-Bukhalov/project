package whim.project.task;

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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import whim.project.marks.Mark;

@Entity
@Table(name = "tasks")
@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Модель задания по учебной дисциплине")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Schema(description = "уникальный идентификатор группы")
	private Long id;

	@Column(name = "title", nullable = false, length = 64)
	@Schema(description = "название работы")
	private String title;

	@Column(name = "value", nullable = false)
	@Schema(description = "макс балл за задание")
	private Integer value;

	@Column(name = "first_value", nullable = false)
	@Schema(description = "макс первичный балл за задание")
	private Integer firstValue;

	@Column(name = "task_group_id", nullable = false)
	@Schema(description = "уникальный идентификатор группы заданий")
	private Long taskGroupId;

	@OneToMany(orphanRemoval = true, targetEntity = Mark.class)
	@JoinColumn(name = "task_id")
	@Schema(hidden = true)
	@JsonIgnore
	private Set<Mark> marks = new HashSet<>();

	Task(String title, Integer value, Integer firstValue, Long TaskGroupId) {
		this.title = title;
		this.value = value;
		this.firstValue = firstValue;
		this.taskGroupId = TaskGroupId;
	}

}
