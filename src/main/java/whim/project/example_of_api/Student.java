package whim.project.example_of_api;
// package whim.project.Student;

// import javax.persistence.Column;
// import javax.persistence.Entity;
// import javax.persistence.JoinColumn;
// import javax.persistence.ManyToOne;
// import javax.persistence.OneToMany;

// import io.swagger.v3.oas.annotations.media.Schema;
// import whim.project.models.Group;

// @Schema(description = "Представляет собой модель студента")
// @Entity
// public class Student {

// @Schema(description = "уникальный идентификатор студента")
// private Integer id;

// @Schema(description = "Имя студента")
// private String firstName;

// @Schema(description = "Фамилия студента")
// private String lastName;

// @Schema(description = "Группа в которой учится студент")
// @ManyToOne
// @JoinColumn(name = "group", nullable = false)
// private Group group;

// public Integer getId() {
// return id;
// }

// public String getFirstName() {
// return firstName;
// }

// public String getLastName() {
// return lastName;
// }

// public Student(Integer id, String firstName, String lastName) {
// this.id = id;
// this.firstName = firstName;
// this.lastName = lastName;
// }

// public Student(Integer id, String firstName, String lastName, Group group) {
// this.id = id;
// this.firstName = firstName;
// this.lastName = lastName;
// this.group = group;
// }

// public void setId(Integer id) {
// this.id = id;
// }

// public void setFirstName(String firstName) {
// this.firstName = firstName;
// }

// public void setLastName(String lastName) {
// this.lastName = lastName;
// }

// public Group getGroup() {
// return group;
// }

// public void setGroup(Group group) {
// this.group = group;
// }

// }
