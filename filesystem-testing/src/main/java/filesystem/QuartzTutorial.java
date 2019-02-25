package filesystem;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
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

public class QuartzTutorial {

	public static void main(String[] args) throws SchedulerException {
		Scheduler scheduler = initScheduler();

		JobDetail jobDetail = newJob(HelloJob.class)
				.withIdentity("hello-job")
				.build();
		SimpleTrigger trigger = newTrigger()
				.withIdentity("trigger")
				.withSchedule(simpleSchedule()
						.withIntervalInSeconds(1)
						.repeatForever())
				.build();

		scheduler.scheduleJob(jobDetail, trigger);
	}

	private static Scheduler initScheduler() throws SchedulerException {
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		Scheduler scheduler = schedulerFactory.getScheduler();
		scheduler.start();
		return scheduler;
	}

	public static class HelloJob implements Job {

		private static final Logger logger = LoggerFactory.getLogger(HelloJob.class);

		@Override
		public void execute(JobExecutionContext context) {
			logger.info("Hello, buddy");
		}
	}
}
