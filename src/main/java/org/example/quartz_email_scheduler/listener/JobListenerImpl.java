package org.example.quartz_email_scheduler.listener;


import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

public class JobListenerImpl implements JobListener{

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "JobListenerImpl";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {

        System.out.println("Job to Be executed");
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        // TODO Auto-generated method stub

    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {

        System.out.println("Job execution done");

    }

}