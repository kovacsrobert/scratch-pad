package hello.tx;

import static org.springframework.transaction.annotation.Propagation.NESTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import java.util.function.Supplier;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TxServiceImpl implements TxService {

	@Override
	@Transactional(propagation = REQUIRED)
	public void execute(Runnable action) {
		action.run();
	}

	@Override
	@Transactional(propagation = REQUIRED)
	public <T> T execute(Supplier<T> action) {
		return action.get();
	}

	@Override
	@Transactional(propagation = NESTED)
	public void executeInNested(Runnable action) {
		action.run();
	}

	@Override
	@Transactional(propagation = NESTED)
	public <T> T executeInNested(Supplier<T> action) {
		return action.get();
	}

	@Override
	@Transactional(propagation = REQUIRES_NEW)
	public void executeInNew(Runnable action) {
		action.run();
	}

	@Override
	@Transactional(propagation = REQUIRES_NEW)
	public <T> T executeInNew(Supplier<T> action) {
		return action.get();
	}
}
