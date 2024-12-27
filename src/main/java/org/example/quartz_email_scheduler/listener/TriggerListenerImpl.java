package org.example.quartz_email_scheduler.listener;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerListener;

public class TriggerListenerImpl implements TriggerListener{

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "TriggerListenerImpl";
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {

        System.out.println("Trigger fired");

    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {


    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context,
                                CompletedExecutionInstruction triggerInstructionCode) {

        System.out.println("Trigger completed");
    }

}