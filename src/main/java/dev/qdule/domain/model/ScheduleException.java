package dev.qdule.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ScheduleException {
    private Long id;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String reason;
    private List<ScheduleExceptionBreak> breaks = new ArrayList<>();

    public ScheduleException() {
    }

    public ScheduleException(LocalDateTime startDateTime, LocalDateTime endDateTime, String reason) {
        this(startDateTime, endDateTime, reason, new ArrayList<>());
    }

    public ScheduleException(LocalDateTime startDateTime, LocalDateTime endDateTime, String reason,
            List<ScheduleExceptionBreak> breaks) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.reason = reason;
        setBreaks(breaks);
    }

    public ScheduleException(Long id, LocalDateTime startDateTime, LocalDateTime endDateTime, String reason) {
        this(id, startDateTime, endDateTime, reason, new ArrayList<>());
    }

    public ScheduleException(Long id, LocalDateTime startDateTime, LocalDateTime endDateTime, String reason,
            List<ScheduleExceptionBreak> breaks) {
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

    public List<ScheduleExceptionBreak> getBreaks() {
        return breaks;
    }

    public void setBreaks(List<ScheduleExceptionBreak> breaks) {
        this.breaks = breaks == null ? new ArrayList<>() : breaks;
    }
}
