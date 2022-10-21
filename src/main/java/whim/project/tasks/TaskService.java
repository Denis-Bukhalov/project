package whim.project.tasks;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;

	public List<Task> getAll() {
		return taskRepository.findAll();
	}

	public Optional<Task> findById(long id) {
		return taskRepository.findById(id);
	}

	public Task saveTask(Task task) {
		return taskRepository.save(task);
	}

	public int deleteTask(long id) {
		return taskRepository.deleteTaskById(id);
	}

}
