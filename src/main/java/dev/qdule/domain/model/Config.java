package dev.qdule.domain.model;

public class Config {
    private Long id;
    private String sendEmail;
    private String contactLink;
    private String cancelLink;
    private String rescheduleLink;

    public Config() {
    }

    public Config(String sendEmail, String contactLink, String cancelLink, String rescheduleLink) {
        this.sendEmail = sendEmail;
        this.contactLink = contactLink;
        this.cancelLink = cancelLink;
        this.rescheduleLink = rescheduleLink;
    }

    public Config(Long id, String sendEmail, String contactLink, String cancelLink, String rescheduleLink) {
        this.id = id;
        this.sendEmail = sendEmail;
        this.contactLink = contactLink;
        this.cancelLink = cancelLink;
        this.rescheduleLink = rescheduleLink;
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
