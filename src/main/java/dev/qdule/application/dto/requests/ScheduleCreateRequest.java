package dev.qdule.application.dto.requests;

import java.time.LocalDateTime;

import dev.qdule.domain.model.ScheduleStatus;

public class ScheduleCreateRequest {
    private Long treatmentId;
    private ClientCreateRequest client;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String reason;
    private ScheduleStatus status;

    public Long getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(Long treatmentId) {
        this.treatmentId = treatmentId;
    }

    public ClientCreateRequest getClient() {
        return client;
    }

    public void setClient(ClientCreateRequest client) {
        this.client = client;
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
