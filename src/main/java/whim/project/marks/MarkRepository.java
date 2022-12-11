package whim.project.marks;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {

	@Query(value = "select * from marks where student_id = ?1 and task_id = ?2", nativeQuery = true)
	List<Mark> existByStudentIdAndTaskId(Long studentId, Long taskId);

	@Transactional
	List<Mark> removeById(Long id);
}
