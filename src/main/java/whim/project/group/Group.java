package whim.project.group;

import java.util.Set;

import javax.persistence.CascadeType;
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

	@Column(name = "title", unique = true, nullable = false, length = 16)
	@Schema(description = "Короткая запись имени группы", nullable = false, maxLength = 16, example = "МО-201")
	private String title;

	@Column(name = "full_name", nullable = true, length = 128)
	@Schema(description = "Полная запись имени группы", nullable = true, maxLength = 128, example = "Математическое обеспечение и администрирование информационных систем")
	private String fullName;

	@OneToMany(orphanRemoval = true, targetEntity = Student.class)
	@JoinColumn(name = "group_id")
	@Schema(hidden = true)
	@JsonIgnore
	private Set<Student> students;

	@OneToMany(orphanRemoval = true, targetEntity = Subject.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "group_id", nullable = false)
	@Schema(hidden = true)
	@JsonIgnore
	private Set<Subject> subjects;
}
