package breaker;

import java.util.concurrent.ExecutionException;

public class App {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        HashBreakerCallableFactory hashBreakerCallableFactory = new HashBreakerCallableFactory();
        HashBreaker hashBreaker = new HashBreaker(hashBreakerCallableFactory);
        String result = hashBreaker.breakHash(getHash(args), getDepth(args), getThreadNumber(args));
        System.out.println("Result: " + result);
    }

    private static String getHash(String[] args) {
        return args[0];
    }

    private static int getDepth(String[] args) {
        return Integer.parseInt(args[1]);
    }

    private static int getThreadNumber(String[] args) {
        return Integer.parseInt(args[2]);
    }
}
