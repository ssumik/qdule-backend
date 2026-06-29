package dev.qdule.application.dto.requests;

public class ConfigUpdateRequest {
    private String sendEmail;
    private String contactLink;
    private String cancelLink;
    private String rescheduleLink;

    public String getSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(String sendEmail) {
        this.sendEmail = sendEmail;
    }

    public String getContactLink() {
        return contactLink;
    }

    public void setContactLink(String contactLink) {
        this.contactLink = contactLink;
    }

    public String getCancelLink() {
        return cancelLink;
    }

    public void setCancelLink(String cancelLink) {
        this.cancelLink = cancelLink;
    }

    public String getRescheduleLink() {
        return rescheduleLink;
    }

    public void setRescheduleLink(String rescheduleLink) {
        this.rescheduleLink = rescheduleLink;
    }
}
