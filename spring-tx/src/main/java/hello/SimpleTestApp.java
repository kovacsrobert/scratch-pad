package hello;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionCallback;

@Component
public class SimpleTestApp {

	private static Logger logger = LoggerFactory.getLogger(SimpleTestApp.class);

	@Autowired
	private DtoRepository dtoRepository;
	@Autowired
	private TxServiceImpl txService;

	public void run() {
		final UUID id1 = UUID.randomUUID();

		txService.execute((TransactionCallback<Void>) status -> {
			dtoRepository.save(id1);
			status.setRollbackOnly();
			return null;
		});

		logger.info("id1: " + dtoRepository.get(id1));
	}
}
