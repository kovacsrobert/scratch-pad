package hello.dto;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DtoRepository {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public DtoRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public boolean save(UUID id) {
		int executeUpdate = jdbcTemplate.update("insert into dto (id) values (?)", new String[] { id.toString() });
		return executeUpdate == 1;
	}

	public Optional<Dto> get(UUID id) {
		try {
			String dbId = jdbcTemplate.queryForObject("select id from dto where id = ?", new String[] { id.toString() }, String.class);
			return Optional.of(new Dto(dbId));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}
}
