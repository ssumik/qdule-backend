package dev.qdule.application.dto.responses;

import java.time.ZonedDateTime;

import dev.qdule.domain.model.ScheduleStatus;

public class ScheduleResponse {
    private Long id;
    private Long treatmentId;
    private Long clientId;
    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;
    private String reason;
    private ScheduleStatus status;

    public ScheduleResponse() {
    }

    public ScheduleResponse(Long id, Long treatmentId, Long clientId, ZonedDateTime startDateTime,
            ZonedDateTime endDateTime, String reason, ScheduleStatus status) {
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

    public Long getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(Long treatmentId) {
        this.treatmentId = treatmentId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
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
