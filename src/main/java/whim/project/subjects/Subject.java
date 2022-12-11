package whim.project.subjects;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import whim.project.lecturer.Lecturer;
import whim.project.task_groups.TaskGroup;

@Entity
@Table(name = "subjects")
@Data
@Schema(description = "Модель учебного предмета")
public class Subject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "уникальный идентификатор группы", example = "1")
	private Long id;

	@Column(name = "title", nullable = false, length = 64)
	@Schema(description = "Название предмета", nullable = false, maxLength = 64, example = "Математический анализ")
	private String title;

	@Column(name = "group_id", nullable = false, updatable = false, insertable = false)
	@Schema(description = "Идентификатор группы которая обучается этому предмету", nullable = false, example = "1")
	private Long groupId;

	@ManyToMany(mappedBy = "subjects")
	@Schema(hidden = true)
	@JsonIgnore
	private Set<Lecturer> lecturers;

	@OneToMany(orphanRemoval = true, targetEntity = TaskGroup.class)
	@JoinColumn(name = "subject_id")
	@JsonIgnore
	@Schema(hidden = true)
	private Set<TaskGroup> taskGroups;

}
