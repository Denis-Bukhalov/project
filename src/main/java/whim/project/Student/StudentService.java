package whim.project.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
	
	List<Student> getAll();
	Optional<Student> getById(final int id);
	
}
