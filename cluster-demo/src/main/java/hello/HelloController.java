package hello;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.cache.LoadingCache;

@RestController
public class HelloController {

    private static final Logger logger = LogManager.getLogger(HelloController.class);

    private final LoadingCache<String, String> helloCache;

    public HelloController(LoadingCache<String, String> helloCache) {
        this.helloCache = helloCache;
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", required = false) String name) throws ExecutionException {
        logger.info("HelloController.hello(" + name + ")");
        return helloCache.get(enrichName(name));
    }

    // FIXME move into component with AOP
    private String enrichName(String name) {
        return Optional.ofNullable(name).orElse("anonymous");
    }
}
