package hello;

import static org.springframework.transaction.TransactionDefinition.ISOLATION_SERIALIZABLE;
import static org.springframework.transaction.TransactionDefinition.PROPAGATION_NESTED;
import static org.springframework.transaction.TransactionDefinition.PROPAGATION_REQUIRED;
import static org.springframework.transaction.TransactionDefinition.PROPAGATION_REQUIRES_NEW;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class TxServiceImpl implements TxService {

	private final PlatformTransactionManager txManager;

	@Autowired
	public TxServiceImpl(PlatformTransactionManager txManager) {
		this.txManager = txManager;
	}

	@Override
	public <T> T execute(TransactionCallback<T> action) {
		DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
		transactionDefinition.setPropagationBehavior(PROPAGATION_REQUIRED);
		transactionDefinition.setIsolationLevel(ISOLATION_SERIALIZABLE);
		TransactionTemplate transactionTemplate = new TransactionTemplate(txManager, transactionDefinition);
		return transactionTemplate.execute(action);
	}

	@Override
	public <T> T  executeNested(TransactionCallback<T> action) {
		DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
		transactionDefinition.setPropagationBehavior(PROPAGATION_NESTED);
		transactionDefinition.setIsolationLevel(ISOLATION_SERIALIZABLE);
		TransactionTemplate transactionTemplate = new TransactionTemplate(txManager, transactionDefinition);
		return transactionTemplate.execute(action);
	}
}
