package whim.project.students;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@Column(name = "first_name", nullable = false, length = 32)
	private String firstName;

	@Column(name = "second_name", nullable = false, length = 32)
	private String secondName;

	// @JsonIgnore
	// @ManyToOne(targetEntity = Group.class, optional = false)
	// @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false)
	// private Group group;

	@Column(name = "group_id", nullable = false, updatable = false, insertable = false)
	private Long groupId;
}
