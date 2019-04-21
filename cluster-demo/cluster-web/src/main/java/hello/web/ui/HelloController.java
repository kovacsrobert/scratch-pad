package hello.web.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import hello.web.provider.HelloProvider;

@RestController
public class HelloController {

    private static final Logger logger = LogManager.getLogger(HelloController.class);

    private final HelloProvider helloProvider;

    public HelloController(HelloProvider helloProvider) {
        this.helloProvider = helloProvider;
    }

    @GetMapping("/hello/{name}")
    public String hello(@PathVariable(value = "name", required = false) String name) {
        logger.info("HelloController.hello(" + name + ")");
        return helloProvider.welcome(enrichName(name));
    }

    // FIXME move into component with AOP
    private String enrichName(String name) {
        return Optional.ofNullable(name).orElse("anonymous");
    }
}
