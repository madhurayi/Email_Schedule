package org.example.quartz_email_scheduler.config;
import org.quartz.*;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class AutowiringJobFactory implements JobFactory {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public Job newJob(TriggerFiredBundle bundle, Scheduler scheduler) throws SchedulerException {
        // Use Spring's application context to create the job bean.
        Class<? extends Job> jobClass = bundle.getJobDetail().getJobClass();
        return applicationContext.getBean(jobClass);
    }

    public Job newJob(JobExecutionContext context) throws JobExecutionException {
        // This method is overridden, but it is usually used when Quartz directly instantiates a job (if needed).
        Class<? extends Job> jobClass = context.getJobDetail().getJobClass();
        return applicationContext.getBean(jobClass);
    }
}
