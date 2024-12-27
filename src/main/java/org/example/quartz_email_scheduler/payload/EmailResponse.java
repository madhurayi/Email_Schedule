package org.example.quartz_email_scheduler.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class EmailResponse {
    private boolean success;
    private String message;
    private String jobId;
    private String jobGroup;
    public EmailResponse(boolean success, String message, String jobId, String jobGroup) {
        this.success = success;
        this.message = message;
        this.jobId = jobId;
        this.jobGroup = jobGroup;
    }
    public EmailResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
