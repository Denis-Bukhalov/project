package whim.project.Student;

import javax.persistence.Entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Представляет собой модель студента")
@Entity
public class Student {

	@Schema(description = "уникальный идентификатор студента")
	private Integer id;

	@Schema(description = "Имя студента")
	private String firstName;

	@Schema(description = "Фамилия студента")
	private String lastName;

	public Integer getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Student(Integer id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	
	
}
