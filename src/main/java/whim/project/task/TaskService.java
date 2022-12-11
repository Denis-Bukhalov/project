package whim.project.task;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
	@Autowired
	private TaskRepository taskRepository;

	public List<Task> getAllTasks() {
		return taskRepository.findAll();
	}

	public Optional<Task> getTaskById(Long id) {
		return taskRepository.findById(id);
	}

	public Task saveTask(Task Task) {
		return taskRepository.save(Task);
	}

	public List<Task> removeById(Long id) {
		return taskRepository.removeById(id);
	}

}
