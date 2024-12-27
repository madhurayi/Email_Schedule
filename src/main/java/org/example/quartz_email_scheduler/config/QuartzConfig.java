package org.example.quartz_email_scheduler.config;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Autowired
    private AutowiringJobFactory autowiringJobFactory;

    @Bean
    public Scheduler scheduler() throws SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        // Tell Quartz to use the AutowiringJobFactory for job instantiation
        scheduler.setJobFactory(autowiringJobFactory);

        return scheduler;
    }
}

