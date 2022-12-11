package whim.project.task_groups;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskGroupService {

	@Autowired
	private TaskGroupRepository taskGroupRepository;

	public List<TaskGroup> getAll() {
		return taskGroupRepository.findAll();
	}

	public Optional<TaskGroup> findById(long id) {
		return taskGroupRepository.findById(id);
	}

	public TaskGroup saveTaskGroup(TaskGroup task) {
		return taskGroupRepository.save(task);
	}

	public int deleteTaskGroup(long id) {
		return taskGroupRepository.deleteTaskById(id);
	}

	public List<TaskGroup> removeById(Long id) {
		return taskGroupRepository.removeById(id);
	}

}
