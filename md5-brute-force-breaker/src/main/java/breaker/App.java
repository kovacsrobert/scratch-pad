package breaker;

import com.google.common.base.Stopwatch;

public class App {

    private static final String DEFAULT_HASH = "4fd0101ea3d0f5abbe296ef97f47afec";
    private static final int DEPTH = 6;

    public static void main(String[] args) {
        Stopwatch timer = Stopwatch.createStarted();
        HashBreakerCallable hashBreakerCallable = new HashBreakerCallable(DEPTH, DEFAULT_HASH);
        String result = hashBreakerCallable.call();
        timer.stop();
        System.out.println("App finished at " + timer);
        System.out.println("Result: " + result);
    }
}
