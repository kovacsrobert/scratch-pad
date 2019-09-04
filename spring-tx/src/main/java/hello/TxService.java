package hello;

import java.util.UUID;

import org.springframework.transaction.support.TransactionCallback;

public interface TxService {

	void execute(TransactionCallback<UUID> action);

	void executeNested(TransactionCallback<UUID> action);
}
