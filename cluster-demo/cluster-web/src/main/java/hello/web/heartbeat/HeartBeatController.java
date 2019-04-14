package hello.web.heartbeat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeartBeatController {

	private static final Logger logger = LogManager.getLogger(HeartBeatController.class);

	@GetMapping("/service/status")
	public @ResponseBody ResponseEntity<Void> ping() {
		logger.info("heartbeat ping");
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
