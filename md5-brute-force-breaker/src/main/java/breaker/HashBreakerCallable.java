package breaker;

import com.google.common.base.Stopwatch;
import com.google.common.hash.Hasher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

import static com.google.common.hash.Hashing.md5;
import static java.nio.charset.StandardCharsets.UTF_8;

class HashBreakerCallable implements Callable<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HashBreakerCallable.class);

    private static final char START_CHAR = 'a';
    private static final char END_CHAR = 'z';

    private final String startingPrefix;
    private final int length;
    private final String secretHash;

    private static volatile boolean solved = false;

    HashBreakerCallable(String startingPrefix, int length, String secretHash) {
        this.startingPrefix = startingPrefix;
        this.length = length;
        this.secretHash = secretHash;
    }

    @Override
    public String call() {
        Stopwatch timer = Stopwatch.createStarted();
        LOGGER.debug("Timer started");
        try {
            recursiveSearch(startingPrefix);
        } catch (RecursionEndedException e) {
            return e.result;
        } finally {
            timer.stop();
            LOGGER.debug("Timer stopped at {}", timer);
        }
        return "";
    }

    private void recursiveSearch(String current) throws RecursionEndedException {
        String hash = hash(current);
        if (hash.equals(secretHash)) {
            solved = true;
            LOGGER.info("Found secret: {}", current);
            throw new RecursionEndedException(current);
        }
        if (current.length() == length) {
            LOGGER.trace("Secret was not found: {}", current);
            return;
        }
        for (int i = START_CHAR; i <= END_CHAR; i++) {
            if (!solved) {
                recursiveSearch(current + (char) i);
            }
        }
    }

    private static String hash(String toHash) {
        Hasher hasher = md5().newHasher();
        hasher.putString(toHash, UTF_8);
        return hasher.hash().toString();
    }

    private static class RecursionEndedException extends Exception {
        private final String result;
        private RecursionEndedException(String result) {
            this.result = result;
        }
    }
}
