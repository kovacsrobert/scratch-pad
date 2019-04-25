package quartz;

import org.quartz.JobExecutionException;

public class HelloException extends JobExecutionException {

	public HelloException(String message) {
		super(message);
		this.setUnscheduleAllTriggers(true);
	}
}
