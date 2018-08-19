package breaker;

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

    private final int length;
    private final String secretHash;

    HashBreakerCallable(int length, String secretHash) {
        this.length = length;
        this.secretHash = secretHash;
    }

    @Override
    public String call() throws Exception {
        try {
            recursiveSearch("");
        } catch (RecursionEndedException e) {
            return e.result;
        }
        return "";
    }

    private void recursiveSearch(String current) throws RecursionEndedException {
        String hash = hash(current);
        if (hash.equals(secretHash)) {
            LOGGER.debug("Found secret: {}", current);
            throw new RecursionEndedException(current);
        }
        if (current.length() == length) {
            LOGGER.debug("Secret was not found: {}", current);
            return;
        }
        for (int i = START_CHAR; i <= END_CHAR; i++) {
            recursiveSearch(current + (char) i);
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
