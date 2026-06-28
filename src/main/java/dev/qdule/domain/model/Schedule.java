package dev.qdule.domain.model;

import java.time.LocalDateTime;

public class Schedule {
    private Long id;
    private Treatment treatmentId;
    private Client clientId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String reason;
    private ScheduleStatus status;

    public Schedule() {
    }

    public Schedule(Treatment treatmentId, Client clientId, LocalDateTime startDateTime, LocalDateTime endDateTime,
            String reason, ScheduleStatus status) {
        this.treatmentId = treatmentId;
        this.clientId = clientId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.reason = reason;
        this.status = status;
    }

    public Schedule(Long id, Treatment treatmentId, Client clientId, LocalDateTime startDateTime,
            LocalDateTime endDateTime,
            String reason, ScheduleStatus status) {
        this.id = id;
        this.treatmentId = treatmentId;
        this.clientId = clientId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.reason = reason;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Treatment getTreatment() {
        return treatmentId;
    }

    public void setTreatment(Treatment treatmentId) {
        this.treatmentId = treatmentId;
    }

    public Client getClient() {
        return clientId;
    }

    public void setClient(Client clientId) {
        this.clientId = clientId;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ScheduleStatus getStatus() {
        return status;
    }

    public void setStatus(ScheduleStatus status) {
        this.status = status;
    }
}
