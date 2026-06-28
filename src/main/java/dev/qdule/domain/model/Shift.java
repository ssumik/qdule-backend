package dev.qdule.domain.model;

import java.time.Duration;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Shift {
    private Long id;
    private String name;
    private LocalTime startTime;
    private LocalTime endTime;
    private Duration restTimeBetweenAppointments;
    private List<ShiftBreak> breaks = new ArrayList<>();
    private DayOfWeek dayOfWeek;
    private ShiftStatus status;

    public Shift() {
    }

    public Shift(String name, LocalTime startTime, LocalTime endTime, Duration restTimeBetweenAppointments,
            List<ShiftBreak> breaks) {
        this(name, startTime, endTime, restTimeBetweenAppointments, breaks, null, ShiftStatus.ENABLED);
    }

    public Shift(String name, LocalTime startTime, LocalTime endTime, Duration restTimeBetweenAppointments,
            List<ShiftBreak> breaks, DayOfWeek dayOfWeek, ShiftStatus status) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.restTimeBetweenAppointments = restTimeBetweenAppointments;
        setBreaks(breaks);
        this.dayOfWeek = dayOfWeek;
        this.status = status;
    }

    public Shift(Long id, String name, LocalTime startTime, LocalTime endTime, Duration restTimeBetweenAppointments,
            List<ShiftBreak> breaks) {
        this(id, name, startTime, endTime, restTimeBetweenAppointments, breaks, null, ShiftStatus.ENABLED);
    }

    public Shift(Long id, String name, LocalTime startTime, LocalTime endTime, Duration restTimeBetweenAppointments,
            List<ShiftBreak> breaks, DayOfWeek dayOfWeek, ShiftStatus status) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.restTimeBetweenAppointments = restTimeBetweenAppointments;
        setBreaks(breaks);
        this.dayOfWeek = dayOfWeek;
        this.status = status;
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

    // TODO: VALIDAR SE PRECISA SER ALTERADO PARA VALOR COM TIMEZONE
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

    public List<ShiftBreak> getBreaks() {
        return breaks;
    }

    public void setBreaks(List<ShiftBreak> breaks) {
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
