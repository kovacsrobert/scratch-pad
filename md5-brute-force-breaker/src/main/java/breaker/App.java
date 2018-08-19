package breaker;

public class App {

    private static final String DEFAULT_HASH = "4fd0101ea3d0f5abbe296ef97f47afec";
    private static final int DEPTH = 6;

    public static void main(String[] args) throws Exception {
        HashBreakerCallable hashBreakerCallable = new HashBreakerCallable(DEPTH, DEFAULT_HASH);
        System.out.println(hashBreakerCallable.call());
    }
}
