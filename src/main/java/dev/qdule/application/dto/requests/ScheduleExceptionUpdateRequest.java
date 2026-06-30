package dev.qdule.application.dto.requests;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ScheduleExceptionUpdateRequest {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String reason;
    private List<ScheduleExceptionBreakRequest> breaks = new ArrayList<>();

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

    public List<ScheduleExceptionBreakRequest> getBreaks() {
        return breaks;
    }

    public void setBreaks(List<ScheduleExceptionBreakRequest> breaks) {
        this.breaks = breaks == null ? new ArrayList<>() : breaks;
    }
}
