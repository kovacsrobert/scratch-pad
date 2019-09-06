package hello.dto;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DtoManager {

	private final DtoRepository dtoRepository;

	@Autowired
	public DtoManager(DtoRepository dtoRepository) {
		this.dtoRepository = dtoRepository;
	}

	public boolean save(UUID id) {
		return dtoRepository.save(id);
	}

	public Optional<Dto> get(UUID id) {
		return dtoRepository.get(id);
	}
}
