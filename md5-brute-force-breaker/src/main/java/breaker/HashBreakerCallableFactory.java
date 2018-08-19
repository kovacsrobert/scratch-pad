package breaker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

class HashBreakerCallableFactory {

    List<Callable<String>> create(String hash, int depth) {
        List<Callable<String>> resultList = new ArrayList<>(26);
        for (char i = 'a'; i <= 'z'; i++) {
            resultList.add(new HashBreakerCallable(String.valueOf(i), depth, hash));
        }
        return resultList;
    }
}
