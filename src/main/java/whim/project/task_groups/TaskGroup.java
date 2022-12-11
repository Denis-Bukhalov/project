package whim.project.task_groups;

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
import whim.project.task.Task;

@Entity
@Table(name = "task_groups")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Модель группы задания")
public class TaskGroup {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "уникальный идентификатор группы задания", example = "1")
	private Long id;

	@Column(name = "title", nullable = true, length = 255)
	@Schema(description = "название группы задания", nullable = true, maxLength = 255)
	private String title;

	@Column(name = "bg_color", nullable = false, length = 7)
	@Schema(description = "цвет фона", nullable = false, maxLength = 7)
	private String bgColor;

	@Column(name = "subject_id", nullable = false, updatable = false, insertable = true)
	@Schema(description = "уникальный идентификатор предмета соответствующей группы заданий", nullable = false)
	private Long subjectId;

	@OneToMany(orphanRemoval = true, targetEntity = Task.class)
	@JoinColumn(name = "task_group_id")
	@Schema(hidden = true)
	@JsonIgnore
	private Set<Task> tasks = new HashSet<>();

	public TaskGroup(String title, String bgColor, Long subjectId) {
		this.title = title;
		this.subjectId = subjectId;
		this.bgColor = bgColor;
	}

}
