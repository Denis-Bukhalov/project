package whim.project.students;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
	@Autowired
	private StudentRepository studentRepository;

	public List<Student> getAll() {
		return studentRepository.findAll();
	}

	public Optional<Student> getStudentById(long id) {
		return studentRepository.findById(id);
	}
}
