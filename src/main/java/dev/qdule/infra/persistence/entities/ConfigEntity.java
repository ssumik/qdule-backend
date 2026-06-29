package dev.qdule.infra.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "configs")
public class ConfigEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "send_email", length = 100)
    private String sendEmail;

    @Column(name = "contact_link", length = 500)
    private String contactLink;

    @Column(name = "cancel_link", length = 500)
    private String cancelLink;

    @Column(name = "reschedule_link", length = 500)
    private String rescheduleLink;

    public ConfigEntity() {
    }

    public ConfigEntity(Long id, String sendEmail, String contactLink, String cancelLink, String rescheduleLink) {
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
