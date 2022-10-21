package whim.project.tasks;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

	@Modifying
	@Query(value = "DELETE FROM tasks WHERE id = ?1", nativeQuery = true)
	@Transactional
	int deleteTaskById(Long id);

}
