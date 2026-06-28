package dev.qdule.application.dto.responses;

import java.time.LocalTime;

public class ShiftBreakResponse {
    private Long id;
    private LocalTime startTime;
    private LocalTime endTime;

    public ShiftBreakResponse() {
    }

    public ShiftBreakResponse(Long id, LocalTime startTime, LocalTime endTime) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
