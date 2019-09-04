package hello;

import java.util.UUID;

import org.springframework.transaction.support.TransactionCallback;

public class TxServiceImpl implements TxService {



	@Override
	public void execute(TransactionCallback<UUID> action) {

	}

	@Override
	public void executeNested(TransactionCallback<UUID> action) {

	}
}
