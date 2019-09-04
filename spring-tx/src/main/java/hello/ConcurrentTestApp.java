package hello;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionCallback;

@Component
public class ConcurrentTestApp {

	private static Logger logger = LoggerFactory.getLogger(ConcurrentTestApp.class);

	@Autowired
	private DtoRepository dtoRepository;
	@Autowired
	private TxServiceImpl txService;

	public void run(String... args) throws InterruptedException {

		final UUID id1 = UUID.randomUUID();
		final UUID id2 = UUID.randomUUID();

		Thread thread1 = new Thread(() -> {
			txService.execute((TransactionCallback<Void>) status -> {
				dtoRepository.save(id1);
				logger.info("id1: " + dtoRepository.get(id1));
				logger.info("id2: " + dtoRepository.get(id2));
				sleep(2);
				status.setRollbackOnly();
				return null;
			});
		});

		Thread thread2 = new Thread(() -> {
			txService.execute((TransactionCallback<Void>) status -> {
				sleep(1);
				dtoRepository.save(id2);
				logger.info("id1: " + dtoRepository.get(id1));
				logger.info("id2: " + dtoRepository.get(id2));
				sleep(1);
				status.setRollbackOnly();
				return null;
			});
		});

		thread1.start();
		thread2.start();

		thread1.join();
		thread2.join();

		logger.info("id1: " + dtoRepository.get(id1));
		logger.info("id2: " + dtoRepository.get(id2));
	}

	private static void sleep(int sleepInterval) {
		try {
			TimeUnit.SECONDS.sleep(sleepInterval);
		} catch (InterruptedException e) {
			logger.warn("interrupted sleep");
		}
	}

}
