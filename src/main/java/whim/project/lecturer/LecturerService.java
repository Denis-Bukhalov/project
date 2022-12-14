package whim.project.lecturer;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LecturerService {
	@Autowired
	private LecturerRepository lecturerRepository;

	public List<Lecturer> getAll() {
		return lecturerRepository.findAll();
	}

	public Optional<Lecturer> findById(long id) {
		return lecturerRepository.findById(id);
	}
}
