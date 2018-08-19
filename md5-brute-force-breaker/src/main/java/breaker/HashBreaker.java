package breaker;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.*;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isBlank;

class HashBreaker {

    private static final Logger LOGGER = LoggerFactory.getLogger(HashBreaker.class);

    private final HashBreakerCallableFactory hashBreakerCallableFactory;

    HashBreaker(HashBreakerCallableFactory hashBreakerCallableFactory) {
        this.hashBreakerCallableFactory = hashBreakerCallableFactory;
    }

    String breakHash(String hash, int depth, int threadNumber) throws ExecutionException, InterruptedException {
        String secret = "Not found";
        LOGGER.info("App started");
        // start timer
        Stopwatch timer = Stopwatch.createStarted();
        // start and add callables to execution service
        ExecutorService executorService = Executors.newFixedThreadPool(threadNumber);
        List<Callable<String>> callableList = hashBreakerCallableFactory.create(hash, depth);
        List<Future<String>> resultList = callableList.stream()
                .map(executorService::submit)
                .collect(toList());
        LOGGER.info("All thread configured and started");
        // show results from threads
        for (int i = 0; i < resultList.size(); i++) {
            String result = resultList.get(i).get();
            LOGGER.debug("Result " + i + " : " + result);
            if (!isBlank(result)) {
                secret = result;
            }
        }
        // stop timer, show execution time
        timer.stop();
        LOGGER.info("App finished at " + timer);
        // stop executor service
        executorService.shutdown();
        return secret;
    }
}
