package whim.project.marks;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// TODO: add documentation 

@Entity
@Table(name = "marks", uniqueConstraints = @UniqueConstraint(columnNames = { "student_id", "task_id" }))
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Schema(description = "Модель Оценки за задание")
public class Mark implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Schema(description = "уникальный идентификатор оценки")
	private Long id;

	@Column(name = "student_id", nullable = false)
	@Schema(description = "идентификатор студента получившего оценку")
	private Long studentId;

	@Column(name = "task_id", nullable = false)
	@Schema(description = "идентификатор задания")
	private Long taskId;

	@Column(name = "value")
	private Double value;

	public Mark(Long studentId, Long taskId, Double value) {
		this.studentId = studentId;
		this.taskId = taskId;
		this.value = value;
	}

}
