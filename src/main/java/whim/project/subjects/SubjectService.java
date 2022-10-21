package whim.project.subjects;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {

	@Autowired
	private SubjectRepository subjectRepository;

	public List<Subject> getAll() {
		return subjectRepository.findAll();
	}

	public Optional<Subject> findById(long id) {
		return subjectRepository.findById(id);
	}

}
