package dev.qdule.application.dto.responses;

import java.time.Duration;
import java.time.LocalTime;

public class ShiftResponse {
    private Long id;
    private String name;
    private LocalTime startTime;
    private LocalTime endTime;
    private Duration restTimeBetweenAppointments;
    private LocalTime breakStartTime;
    private LocalTime breakEndTime;

    public ShiftResponse() {
    }

    public ShiftResponse(Long id, String name, LocalTime startTime, LocalTime endTime,
            Duration restTimeBetweenAppointments, LocalTime breakStartTime, LocalTime breakEndTime) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.restTimeBetweenAppointments = restTimeBetweenAppointments;
        this.breakStartTime = breakStartTime;
        this.breakEndTime = breakEndTime;
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

    public LocalTime getBreakStartTime() {
        return breakStartTime;
    }

    public void setBreakStartTime(LocalTime breakStartTime) {
        this.breakStartTime = breakStartTime;
    }

    public LocalTime getBreakEndTime() {
        return breakEndTime;
    }

    public void setBreakEndTime(LocalTime breakEndTime) {
        this.breakEndTime = breakEndTime;
    }
}
