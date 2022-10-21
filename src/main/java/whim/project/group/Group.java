package whim.project.group;

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
import lombok.Data;
import whim.project.students.Student;
import whim.project.subjects.Subject;

@Entity
@Table(name = "groups")
@Data
@Schema(description = "Модель группы студентов")
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "уникальный идентификатор группы", example = "1")
	private Long id;

	@Column(name = "short_name", unique = true, nullable = false, length = 8)
	@Schema(description = "Короткая запись имени группы", nullable = false, maxLength = 8, example = "МО-201")
	private String shortName;

	@Column(name = "full_name", nullable = true, length = 50)
	@Schema(description = "Полная запись имени группы", nullable = true, maxLength = 50, example = "Математическое обеспечение и администрирование информационных систем")
	private String fullName;

	@OneToMany(orphanRemoval = true, targetEntity = Student.class)
	@JoinColumn(name = "group_id")
	@Schema(hidden = true)
	@JsonIgnore
	private Set<Student> students;

	@OneToMany(orphanRemoval = true, targetEntity = Subject.class)
	@JoinColumn(name = "group_id")
	@Schema(hidden = true)
	@JsonIgnore
	private Set<Subject> subjects;
}
