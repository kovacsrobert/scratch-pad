package hello;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DtoRepository {

	private static Logger logger = LoggerFactory.getLogger(DtoRepository.class);

	private final DataSource dataSource;

	@Autowired
	public DtoRepository(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public boolean save(UUID id) {
		try {
			Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("insert into dto (id) values (?)");
			preparedStatement.setString(1, id.toString());
			int executeUpdate = preparedStatement.executeUpdate();
			return executeUpdate == 1;
		} catch (SQLException e) {
			logger.error("Failed to save dto", e);
			return false;
		}
	}

	public Optional<Dto> get(UUID id) {
		try {
			Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("select id from dto where id = ?");
			preparedStatement.setString(1, id.toString());
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return Optional.of(new Dto(id.toString()));
			} else {
				return Optional.empty();
			}
		} catch (SQLException e) {
			logger.error("Failed to fetch dto", e);
			return Optional.empty();
		}
	}
}
