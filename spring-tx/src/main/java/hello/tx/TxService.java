package hello.tx;

import java.util.function.Supplier;

public interface TxService {

	void execute(Runnable action);
	<T> T execute(Supplier<T> action);

	void executeInNested(Runnable action);
	<T> T executeInNested(Supplier<T> action);

	void executeInNew(Runnable action);
	<T> T executeInNew(Supplier<T> action);
}
