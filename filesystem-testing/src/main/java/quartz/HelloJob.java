package quartz;

import java.util.Date;
import java.util.Properties;

import org.quartz.Calendar;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SchedulerListener;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.TriggerListener;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.DailyCalendar;
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

    private HelloJobContext jobContext;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        if ("Brian".equals(jobContext.getGuestName()) && jobContext.getGreetingsCounter() >= 3) {
            throw new HelloException("Brian leaves");
        }
        //context.getMergedJobDataMap().put(GREETINGS_COUNTER, ++greetingsCounter);
        stopExecution(1000L);
        jobContext.increaseCounter();
        //context.getJobDetail().getJobDataMap().put(GREETINGS_COUNTER, ++greetingsCounter);
        logger.info("Hello, " + jobContext.getGuestName() + " for the " + jobContext.getGreetingsCounter() + ". times. Run-time: " + context.getFireTime());
    }

    private void stopExecution(long millis) throws JobExecutionException {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new JobExecutionException(e);
        }
    }

    public void setJobContext(HelloJobContext jobContext) {
        this.jobContext = jobContext;
    }

    public static void main(String[] args) throws SchedulerException {

        Properties properties = new Properties();

        properties.put(PROP_THREAD_POOL_CLASS, "org.quartz.simpl.SimpleThreadPool");
        properties.put(PROP_THREAD_POOL_PREFIX + ".threadCount", "1");

        SchedulerFactory schedulerFactory = new StdSchedulerFactory(properties);
        Scheduler scheduler = schedulerFactory.getScheduler();

//        ListenerManager listenerManager = scheduler.getListenerManager();
//        listenerManager.addJobListener(new HelloJobListener());
//        listenerManager.addTriggerListener(new HelloTriggerListener());
//        listenerManager.addSchedulerListener(new HelloSchedulerListener());

        Calendar fullBlockCalendar = new DailyCalendar(new Date().getTime(), new Date().getTime() + 1_000_000L);
        scheduler.addCalendar("fullBlockCalendar", fullBlockCalendar, false, false);

        JobDataMap mikeJobDataMap = new JobDataMap();
        mikeJobDataMap.put("jobContext", new HelloJobContext(0, "Mike"));

        JobDataMap tobyJobDataMap = new JobDataMap();
        tobyJobDataMap.put("jobContext", new HelloJobContext(0, "Toby"));

        JobDataMap brianJobDataMap = new JobDataMap();
        brianJobDataMap.put("jobContext", new HelloJobContext(0, "Brian"));

        JobDetail mikeJobDetail = newJob(HelloJob.class)
                .withIdentity("mike", HELLO_GROUP)
                .setJobData(mikeJobDataMap)
                .build();
        JobDetail tobyJobDetail = newJob(HelloJob.class)
                .withIdentity("toby", HELLO_GROUP)
                .setJobData(tobyJobDataMap)
                .build();
        JobDetail brianJobDetail = newJob(HelloJob.class)
                .withIdentity("brian", HELLO_GROUP)
                .setJobData(brianJobDataMap)
                .build();

        SimpleTrigger mikeTrigger = newTrigger()
                .withIdentity("mike", HELLO_GROUP)
                .modifiedByCalendar("fullBlockCalendar")
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
        SimpleTrigger brianTrigger = newTrigger()
                .withIdentity("brian", HELLO_GROUP)
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(1)
                        .repeatForever())
                .build();

        scheduler.scheduleJob(mikeJobDetail, mikeTrigger);
        //scheduler.scheduleJob(tobyJobDetail, tobyTrigger);
        scheduler.scheduleJob(brianJobDetail, brianTrigger);

        scheduler.start();
    }

    public static class HelloTriggerListener implements TriggerListener {

        private static final Logger logger = LoggerFactory.getLogger(HelloTriggerListener.class);

        @Override
        public String getName() {
            logger.info("HelloTriggerListener.getName");
            return "HelloTriggerListener";
        }

        @Override
        public void triggerFired(Trigger trigger, JobExecutionContext context) {
            logger.info("HelloTriggerListener.triggerFired");
        }

        @Override
        public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
            logger.info("HelloTriggerListener.vetoJobExecution");
            return false;
        }

        @Override
        public void triggerMisfired(Trigger trigger) {
            logger.info("HelloTriggerListener.triggerMisfired");
        }

        @Override
        public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode) {
            logger.info("HelloTriggerListener.triggerComplete");
        }
    }

    public static class HelloJobListener implements JobListener {

        private static final Logger logger = LoggerFactory.getLogger(HelloJobListener.class);

        @Override
        public String getName() {
            logger.info("HelloJobListener.getName");
            return "HelloJobListener";
        }

        @Override
        public void jobToBeExecuted(JobExecutionContext context) {
            logger.info("HelloJobListener.jobToBeExecuted");
        }

        @Override
        public void jobExecutionVetoed(JobExecutionContext context) {
            logger.info("HelloJobListener.jobExecutionVetoed");
        }

        @Override
        public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
            logger.info("HelloJobListener.jobWasExecuted");
        }
    }

    public static class HelloSchedulerListener implements SchedulerListener {

        private static final Logger logger = LoggerFactory.getLogger(HelloSchedulerListener.class);

        @Override
        public void jobScheduled(Trigger trigger) {
            logger.info("HelloSchedulerListener.jobScheduled");
        }

        @Override
        public void jobUnscheduled(TriggerKey triggerKey) {
            logger.info("HelloSchedulerListener.jobUnscheduled");
        }

        @Override
        public void triggerFinalized(Trigger trigger) {
            logger.info("HelloSchedulerListener.triggerFinalized");
        }

        @Override
        public void triggerPaused(TriggerKey triggerKey) {
            logger.info("HelloSchedulerListener.triggerPaused");
        }

        @Override
        public void triggersPaused(String triggerGroup) {
            logger.info("HelloSchedulerListener.triggersPaused");
        }

        @Override
        public void triggerResumed(TriggerKey triggerKey) {
            logger.info("HelloSchedulerListener.triggerResumed");
        }

        @Override
        public void triggersResumed(String triggerGroup) {
            logger.info("HelloSchedulerListener.triggersResumed");
        }

        @Override
        public void jobAdded(JobDetail jobDetail) {
            logger.info("HelloSchedulerListener.jobAdded");
        }

        @Override
        public void jobDeleted(JobKey jobKey) {
            logger.info("HelloSchedulerListener.jobDeleted");
        }

        @Override
        public void jobPaused(JobKey jobKey) {
            logger.info("HelloSchedulerListener.jobPaused");
        }

        @Override
        public void jobsPaused(String jobGroup) {
            logger.info("HelloSchedulerListener.jobsPaused");
        }

        @Override
        public void jobResumed(JobKey jobKey) {
            logger.info("HelloSchedulerListener.jobResumed");
        }

        @Override
        public void jobsResumed(String jobGroup) {
            logger.info("HelloSchedulerListener.jobsResumed");
        }

        @Override
        public void schedulerError(String msg, SchedulerException cause) {
            logger.info("HelloSchedulerListener.schedulerError");
        }

        @Override
        public void schedulerInStandbyMode() {
            logger.info("HelloSchedulerListener.schedulerInStandbyMode");
        }

        @Override
        public void schedulerStarted() {
            logger.info("HelloSchedulerListener.schedulerStarted");
        }

        @Override
        public void schedulerStarting() {
            logger.info("HelloSchedulerListener.schedulerStarting");
        }

        @Override
        public void schedulerShutdown() {
            logger.info("HelloSchedulerListener.schedulerShutdown");
        }

        @Override
        public void schedulerShuttingdown() {
            logger.info("HelloSchedulerListener.schedulerShuttingdown");
        }

        @Override
        public void schedulingDataCleared() {
            logger.info("HelloSchedulerListener.schedulingDataCleared");
        }
    }
}
