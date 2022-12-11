package whim.project.task_groups;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TaskGroupRepository extends JpaRepository<TaskGroup, Long> {

	// TODO(denis): remove this ASAP
	@Modifying
	@Query(value = "DELETE FROM task_groups WHERE id = ?1", nativeQuery = true)
	@Transactional
	int deleteTaskById(Long id);

	@Transactional
	List<TaskGroup> removeById(Long id);
}
