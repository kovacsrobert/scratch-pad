package hello;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import hello.dto.DtoManager;
import hello.tx.TxServiceImpl;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TransactionTest {

	private static Logger logger = LoggerFactory.getLogger(TransactionTest.class);

	@Autowired
	private DtoManager dtoManager;
	@Autowired
	private TxServiceImpl txService;

	@Test
	public void testCreateDtoInTx() {
		final UUID id1 = UUID.randomUUID();

		txService.execute(() -> {
			dtoManager.save(id1);
		});

		assertTrue(dtoManager.get(id1).isPresent());
		assertEquals(dtoManager.get(id1).get().getId(), id1.toString());
	}

	@Test
	public void testCreateRollbackInTx() {
		final UUID id1 = UUID.randomUUID();

		try {
			txService.execute(() -> {
				dtoManager.save(id1);
				throw new RuntimeException("rollback");
			});
		} catch (Throwable e) {
			logger.info("Expected exception: {}", e.getMessage());
		}

		assertFalse(dtoManager.get(id1).isPresent());
	}
}