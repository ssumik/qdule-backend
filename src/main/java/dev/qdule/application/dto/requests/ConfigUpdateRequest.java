package dev.qdule.application.dto.requests;

public class ConfigUpdateRequest {
    private String sendEmail;
    private String contactLink;

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
}
