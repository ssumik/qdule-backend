package dev.qdule.application.dto.responses;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ScheduleExceptionResponse {
    private Long id;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String reason;
    private List<ScheduleExceptionBreakResponse> breaks = new ArrayList<>();

    public ScheduleExceptionResponse() {
    }

    public ScheduleExceptionResponse(Long id, LocalDateTime startDateTime, LocalDateTime endDateTime, String reason) {
        this.id = id;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.reason = reason;
    }

    public ScheduleExceptionResponse(Long id, LocalDateTime startDateTime, LocalDateTime endDateTime, String reason,
            List<ScheduleExceptionBreakResponse> breaks) {
        this.id = id;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.reason = reason;
        setBreaks(breaks);
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

    public List<ScheduleExceptionBreakResponse> getBreaks() {
        return breaks;
    }

    public void setBreaks(List<ScheduleExceptionBreakResponse> breaks) {
        this.breaks = breaks == null ? new ArrayList<>() : breaks;
    }
}
