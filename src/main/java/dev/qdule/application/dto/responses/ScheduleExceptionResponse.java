package dev.qdule.application.dto.responses;

import java.time.ZonedDateTime;

public class ScheduleExceptionResponse {
    private Long id;
    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;
    private String reason;

    public ScheduleExceptionResponse() {
    }

    public ScheduleExceptionResponse(Long id, ZonedDateTime startDateTime, ZonedDateTime endDateTime, String reason) {
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
}
