package dev.qdule.application.dto.responses;

public class EmailSendResponse {
    private String emailId;
    private Long clientId;
    private Long scheduleId;
    private String to;

    public EmailSendResponse() {
    }

    public EmailSendResponse(String emailId, Long clientId, Long scheduleId, String to) {
        this.emailId = emailId;
        this.clientId = clientId;
        this.scheduleId = scheduleId;
        this.to = to;
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
}
