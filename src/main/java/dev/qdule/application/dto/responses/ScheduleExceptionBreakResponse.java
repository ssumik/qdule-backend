package dev.qdule.application.dto.responses;

import java.time.LocalDateTime;

public class ScheduleExceptionBreakResponse {
    private Long id;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public ScheduleExceptionBreakResponse() {
    }

    public ScheduleExceptionBreakResponse(Long id, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.id = id;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
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
}
