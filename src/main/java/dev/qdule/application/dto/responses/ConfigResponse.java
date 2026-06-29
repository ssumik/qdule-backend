package dev.qdule.application.dto.responses;

public class ConfigResponse {
    private Long id;
    private String sendEmail;
    private String contactLink;

    public ConfigResponse() {
    }

    public ConfigResponse(Long id, String sendEmail, String contactLink) {
        this.id = id;
        this.sendEmail = sendEmail;
        this.contactLink = contactLink;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
