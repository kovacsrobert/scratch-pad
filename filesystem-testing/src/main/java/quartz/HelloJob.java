package quartz;

import java.util.Properties;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.impl.StdSchedulerFactory.PROP_THREAD_POOL_CLASS;
import static org.quartz.impl.StdSchedulerFactory.PROP_THREAD_POOL_PREFIX;

@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class HelloJob implements Job {

	private static final Logger logger = LoggerFactory.getLogger(HelloJob.class);

	private static final String HELLO_GROUP = "hello-group";
	private static final String GREETINGS_COUNTER = "greetingsCounter";
	private static final String GUEST_NAME = "guestName";

	private int greetingsCounter;
	private String guestName;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		//context.getMergedJobDataMap().put(GREETINGS_COUNTER, ++greetingsCounter);
		stopExecution(2000L);
		context.getJobDetail().getJobDataMap().put(GREETINGS_COUNTER, ++greetingsCounter);
		logger.info("Hello, " + guestName + " for the " + greetingsCounter + ". times. Run-time: " + context.getFireTime());
	}

	private void stopExecution(long millis) throws JobExecutionException {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			throw new JobExecutionException(e);
		}
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public void setGreetingsCounter(int greetingsCounter) {
		this.greetingsCounter = greetingsCounter;
	}

	public static void main(String[] args) throws SchedulerException {

		Properties properties = new Properties();

		properties.put(PROP_THREAD_POOL_CLASS, "org.quartz.simpl.SimpleThreadPool");
		properties.put(PROP_THREAD_POOL_PREFIX + ".threadCount", "1");

		SchedulerFactory schedulerFactory = new StdSchedulerFactory(properties);
		Scheduler scheduler = schedulerFactory.getScheduler();
		scheduler.start();

		JobDetail mikeJobDetail = newJob(HelloJob.class)
				.withIdentity("mike", HELLO_GROUP)
				.usingJobData(GREETINGS_COUNTER, 0)
				.usingJobData(GUEST_NAME, "Mike")
				.build();
		JobDetail tobyJobDetail = newJob(HelloJob.class)
				.withIdentity("toby", HELLO_GROUP)
				.usingJobData(GREETINGS_COUNTER, 0)
				.usingJobData(GUEST_NAME, "Toby")
				.build();

		SimpleTrigger mikeTrigger = newTrigger()
				.withIdentity("mike", HELLO_GROUP)
				.withSchedule(simpleSchedule()
						.withIntervalInSeconds(1)
						.repeatForever())
				.build();
		SimpleTrigger tobyTrigger = newTrigger()
				.withIdentity("toby", HELLO_GROUP)
				.withSchedule(simpleSchedule()
						.withIntervalInSeconds(1)
						.repeatForever())
				.build();

		scheduler.scheduleJob(mikeJobDetail, mikeTrigger);
		scheduler.scheduleJob(tobyJobDetail, tobyTrigger);
	}
}
