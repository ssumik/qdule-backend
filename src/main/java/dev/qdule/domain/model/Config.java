package dev.qdule.domain.model;

public class Config {
    private Long id;
    private String sendEmail;
    private String contactLink;

    public Config() {
    }

    public Config(String sendEmail, String contactLink) {
        this.sendEmail = sendEmail;
        this.contactLink = contactLink;
    }

    public Config(Long id, String sendEmail, String contactLink) {
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
