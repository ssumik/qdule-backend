package dev.qdule.application.dto.responses;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ShiftResponse {
    private Long id;
    private String name;
    private LocalTime startTime;
    private LocalTime endTime;
    private Duration restTimeBetweenAppointments;
    private List<ShiftBreakResponse> breaks = new ArrayList<>();

    public ShiftResponse() {
    }

    public ShiftResponse(Long id, String name, LocalTime startTime, LocalTime endTime,
            Duration restTimeBetweenAppointments, List<ShiftBreakResponse> breaks) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.restTimeBetweenAppointments = restTimeBetweenAppointments;
        this.breaks = breaks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<ShiftBreakResponse> getBreaks() {
        return breaks;
    }

    public void setBreaks(List<ShiftBreakResponse> breaks) {
        this.breaks = breaks == null ? new ArrayList<>() : breaks;
    }
}
