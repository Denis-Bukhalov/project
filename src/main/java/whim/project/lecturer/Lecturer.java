package whim.project.lecturer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "lecturers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Schema(description = "Модель преподавателя")
public class Lecturer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Schema(description = "уникальный идентификатор преподавателя", example = "1")
	private Long id;

	@Column(name = "fist_name", nullable = false, length = 32)
	@Schema(description = "Имя преподавателя", nullable = false, maxLength = 32, example = "Иван")
	private String firstName;

	@Column(name = "second_name", nullable = false, length = 32)
	@Schema(description = "Фамилия преподавателя", nullable = false, maxLength = 32, example = "Иванов")
	private String secondName;

}
