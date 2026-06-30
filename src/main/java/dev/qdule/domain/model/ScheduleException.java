package dev.qdule.domain.model;

import java.time.LocalDateTime;

public class ScheduleException {
    private Long id;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String reason;

    public ScheduleException() {
    }

    public ScheduleException(LocalDateTime startDateTime, LocalDateTime endDateTime, String reason) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.reason = reason;
    }

    public ScheduleException(Long id, LocalDateTime startDateTime, LocalDateTime endDateTime, String reason) {
        this.id = id;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.reason = reason;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
