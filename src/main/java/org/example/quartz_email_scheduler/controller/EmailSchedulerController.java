package org.example.quartz_email_scheduler.controller;

import static org.quartz.JobBuilder.*;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import static org.quartz.CronScheduleBuilder.*;
import org.example.quartz_email_scheduler.listener.JobListenerImpl;
import org.example.quartz_email_scheduler.listener.TriggerListenerImpl;
import org.example.quartz_email_scheduler.payload.EmailRequest;
import org.example.quartz_email_scheduler.payload.EmailResponse;
import org.example.quartz_email_scheduler.quartzJob.EmailJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.UUID;

@Slf4j
@RestController
public class EmailSchedulerController {

    @Autowired
    private Scheduler scheduler;

    @PostMapping("/schedule/email")
    public ResponseEntity<EmailResponse> scheduleEmail(@Valid @RequestBody EmailRequest emailRequest) {
        try{
            // Grab the Scheduler instance from the Factory
//            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
            Scheduler scheduler = schedFact.getScheduler();
            // and start it off
            scheduler.start();


            ZonedDateTime dateTime = ZonedDateTime.of(emailRequest.getDateTime(),emailRequest.getTimeZone());
            if(dateTime.isBefore(ZonedDateTime.now())){
                EmailResponse emailResponse= new EmailResponse(false,
                        "Date time must be after current time");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(emailResponse);
            }
            JobDetail jobDetail=buildJobDetail(emailRequest);
            Trigger trigger=buildTrigger(jobDetail,dateTime);
            scheduler.getListenerManager().addTriggerListener(new TriggerListenerImpl());
            scheduler.getListenerManager().addJobListener(new JobListenerImpl());
            scheduler.scheduleJob(jobDetail,trigger);
            EmailResponse emailResponse= new EmailResponse(true,jobDetail.getKey().getName(),jobDetail.getKey().getGroup(),
                    "Email Scheduled Successfully");
//            scheduler.shutdown();
            return ResponseEntity.status(HttpStatus.OK).body(new EmailResponse(true, "Email scheduled"));

        }catch(SchedulerException e){
            log.error(e.getMessage());
            EmailResponse emailResponse= new EmailResponse(false,"Error while scheduling email, Please try again later");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(emailResponse);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getData(){
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }
    private JobDetail buildJobDetail(EmailRequest emailRequest) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("email", emailRequest.getEmail());
        jobDataMap.put("subject", emailRequest.getSubject());
        jobDataMap.put("body", emailRequest.getBody());
        return newJob(EmailJob.class)
                .withIdentity(UUID.randomUUID().toString(), "email-jobs")
                .withDescription("Send Email Job")
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    private Trigger buildTrigger(JobDetail jobDetail, ZonedDateTime startAT) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "email-triggers")
                .withDescription("Send Email Trigger")
                .startAt(Date.from(startAT.toInstant()))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();

    }
}
