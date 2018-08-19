package breaker;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    private static final String DEFAULT_HASH = "4fd0101ea3d0f5abbe296ef97f47afec";
    private static final String GG_HASH = "73c18c59a39b18382081ec00bb456d43";

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // start timer
        Stopwatch timer = Stopwatch.createStarted();
        // start and add callables to execution service
        LOGGER.info("App started");
        ExecutorService executorService = Executors.newFixedThreadPool(26);
        List<Future<String>> resultList = new LinkedList<>();
        for (char i = 'a'; i <= 'z'; i++) {
            resultList.add(executorService.submit(new HashBreakerCallable(String.valueOf(i), 6, DEFAULT_HASH)));
        }
        LOGGER.debug("All thread configured and started");
        // show results from threads
        for (int i = 0; i < resultList.size(); i++) {
            System.out.println("Result " + i + " : " + resultList.get(i).get());
        }
        // stop executor service
        executorService.shutdown();
        // stop timer, show execution time
        timer.stop();
        System.out.println("App finished at " + timer);
    }
}
