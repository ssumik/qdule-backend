package dev.qdule.application.dto.responses;

import dev.qdule.application.dto.EmailType;

public class EmailSendResponse {
    private String emailId;
    private Long clientId;
    private Long scheduleId;
    private String to;
    private EmailType emailType;

    public EmailSendResponse() {
    }

    public EmailSendResponse(String emailId, Long clientId, Long scheduleId, String to, EmailType emailType) {
        this.emailId = emailId;
        this.clientId = clientId;
        this.scheduleId = scheduleId;
        this.to = to;
        this.emailType = emailType;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public EmailType getEmailType() {
        return emailType;
    }

    public void setEmailType(EmailType emailType) {
        this.emailType = emailType;
    }
}
