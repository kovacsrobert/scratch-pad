package quartz;

import static org.quartz.JobBuilder.newJob;

import java.text.ParseException;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuartzTutorial {

	public static void main(String[] args) throws SchedulerException, ParseException {
		Scheduler scheduler = initScheduler();

		JobDetail jobDetail = newJob(HelloJob.class)
				.withIdentity("hello-job")
				.build();

		MyCronTriggerImpl trigger2 = new MyCronTriggerImpl();
		trigger2.setName("MyCronTriggerImpl");
		trigger2.setCronExpression("0/1 * * * * ? *");
		trigger2.setStartTime(new Date());

		scheduler.scheduleJob(jobDetail, trigger2);
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
		public void execute(JobExecutionContext context) throws HelloException {
			logger.info("Hello, buddy");

			throw new HelloException(".. Bye");
		}
	}
}
