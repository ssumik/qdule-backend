package dev.qdule.application.dto.requests;

import java.time.LocalDateTime;

import dev.qdule.domain.model.ScheduleStatus;

public class ScheduleUpdateRequest {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String reason;
    private ScheduleStatus status;

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
