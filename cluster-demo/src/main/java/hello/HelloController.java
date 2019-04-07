package hello;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class HelloController {

    private static final Logger logger = LogManager.getLogger(HelloController.class);

    private final HelloDao helloDao;

    public HelloController(HelloDao helloDao) {
        this.helloDao = helloDao;
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", required = false) String name) {
        logger.debug("HelloController.hello(" + name + ")");
        return helloDao.welcome(enrichName(name));
    }

    // FIXME move into component with AOP
    private String enrichName(String name) {
        return Optional.ofNullable(name).orElse("anonymous");
    }
}
