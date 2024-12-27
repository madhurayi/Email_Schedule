package org.example.quartz_email_scheduler.quartzJob;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class EmailJob extends QuartzJobBean {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private MailProperties mailProperties;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        try{
            JobDataMap jobDataMap=context.getMergedJobDataMap();
            String subject=jobDataMap.getString("subject");
            String body=jobDataMap.getString("body");
            String recipientEmail=jobDataMap.getString("email");
            sendMail(mailProperties.getUsername(),recipientEmail,subject,body);
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    private void sendMail(String fromEmail, String toEmail,String subject, String body){
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper=new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.toString());
            messageHelper.setSubject(subject);
            messageHelper.setText(body,true);
            messageHelper.setFrom(fromEmail);
            messageHelper.setTo(toEmail);
            mailSender.send(mimeMessage);
        }catch(MessagingException e){

        }
    }
}
