package dev.qdule.application.dto.requests;

import java.time.ZonedDateTime;

public class ScheduleExceptionUpdateRequest {
    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;
    private String reason;

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
