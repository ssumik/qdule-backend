package dev.qdule.application.dto.requests;

import java.time.Duration;
import java.time.LocalTime;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import dev.qdule.domain.model.ShiftStatus;

public class ShiftUpdateRequest {
    private String name;
    private LocalTime startTime;
    private LocalTime endTime;
    private Duration restTimeBetweenAppointments;
    private List<ShiftBreakRequest> breaks = new ArrayList<>();
    private DayOfWeek dayOfWeek;
    private ShiftStatus status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Duration getRestTimeBetweenAppointments() {
        return restTimeBetweenAppointments;
    }

    public void setRestTimeBetweenAppointments(Duration restTimeBetweenAppointments) {
        this.restTimeBetweenAppointments = restTimeBetweenAppointments;
    }

    public List<ShiftBreakRequest> getBreaks() {
        return breaks;
    }

    public void setBreaks(List<ShiftBreakRequest> breaks) {
        this.breaks = breaks == null ? new ArrayList<>() : breaks;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public ShiftStatus getStatus() {
        return status;
    }

    public void setStatus(ShiftStatus status) {
        this.status = status;
    }
}
