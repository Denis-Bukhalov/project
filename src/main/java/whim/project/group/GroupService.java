package whim.project.group;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

	@Autowired
	private GroupRepository groupRepository;

	public List<Group> getAll() {
		return groupRepository.findAll();
	};

	public Optional<Group> findById(final long id) {
		return groupRepository.findById(id);
	}
}
