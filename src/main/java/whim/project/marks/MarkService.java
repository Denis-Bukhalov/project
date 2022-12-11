package whim.project.marks;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarkService {
	@Autowired
	private MarkRepository markRepository;

	public List<Mark> getAllMarks() {
		return markRepository.findAll();
	}

	public Optional<Mark> getMarkById(Long id) {
		return markRepository.findById(id);
	}

	public void deleteMark(Mark mark) {
		markRepository.delete(mark);
	}

	public List<Mark> removeMark(Long id) {
		return markRepository.removeById(id);
	}

	public Mark saveMark(Mark mark) {
		return markRepository.save(mark);
	}

	public boolean MarkExistByStudentIdAndTaskId(Long studentId, Long taskId) {
		List<Mark> list = markRepository.existByStudentIdAndTaskId(studentId, taskId);
		return list.size() != 0;
	}
}
