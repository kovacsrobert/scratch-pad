package hello;

import org.springframework.transaction.support.TransactionCallback;

public interface TxService {

	<T> T execute(TransactionCallback<T> action);

	<T> T executeNested(TransactionCallback<T> action);
}
