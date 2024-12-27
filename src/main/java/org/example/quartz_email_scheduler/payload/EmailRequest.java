package org.example.quartz_email_scheduler.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
public class EmailRequest {

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String subject;

    @NotEmpty
    private String body;

    @NonNull
    private LocalDateTime dateTime;

    @NonNull
    private ZoneId timeZone;

}
