package quartz2;

import static org.quartz.JobBuilder.newJob;

import java.util.Date;
import java.util.HashSet;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuartzTutorial {

	private static Scheduler scheduler = null;

	public static void main(String[] args) throws SchedulerException, InterruptedException {
		try {
			initScheduler();

			for (int i = 0; i < 100; i++) {
				scheduleJob(i);
				TimeUnit.MILLISECONDS.sleep(10);
			}

			TimeUnit.SECONDS.sleep(5);
		} finally {
			if (scheduler != null) {
				scheduler.shutdown();
			}
		}
	}

	private static void initScheduler() throws SchedulerException {
		StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();
		Properties properties = new Properties();
		properties.setProperty("org.quartz.threadPool.threadCount", "1");
		schedulerFactory.initialize(properties);
		scheduler = schedulerFactory.getScheduler();
		scheduler.start();
	}

	private static void scheduleJob(int id) throws SchedulerException {
		final String identity = "hello-job";
		JobDetail jobDetail = newJob(HelloJob.class)
				.withIdentity(identity)
				.storeDurably(true)
				.build();
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity(identity)
				.forJob(jobDetail)
				.startAt(new Date())
				.usingJobData("id", Integer.valueOf(id))
				.build();
		HashSet<Trigger> hashSet = new HashSet<>();
		hashSet.add(trigger);
		scheduler.scheduleJob(jobDetail, hashSet, true);
	}

	public static class HelloJob implements Job {

		private static final Logger logger = LoggerFactory.getLogger(HelloJob.class);

		@Override
		public void execute(JobExecutionContext context) {
			int id = context.getMergedJobDataMap().getInt("id");
			try {
				logger.info("HelloJob started - " + id);
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				logger.info(e.getMessage());
				throw new RuntimeException(e);
			} finally {
				logger.info("HelloJob finished- " + id);
			}
		}
	}
}
