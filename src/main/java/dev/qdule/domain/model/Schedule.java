package dev.qdule.domain.model;

import java.time.ZonedDateTime;

public class Schedule {
    private Long id;
    private Treatment treatmentId;
    private Client clientId;
    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;
    private String reason;
    private ScheduleStatus status;

    public Schedule() {
    }

    public Schedule(Treatment treatmentId, Client clientId, ZonedDateTime startDateTime, ZonedDateTime endDateTime,
            String reason, ScheduleStatus status) {
        this.treatmentId = treatmentId;
        this.clientId = clientId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.reason = reason;
        this.status = status;
    }

    public Schedule(Long id, Treatment treatmentId, Client clientId, ZonedDateTime startDateTime,
            ZonedDateTime endDateTime,
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

    public ZonedDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(ZonedDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public ZonedDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(ZonedDateTime endDateTime) {
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
