package dev.qdule.domain.model;

import java.time.DayOfWeek;

public class WorkSchedule {
    private Long id;
    private Shift shift;
    private DayOfWeek dayOfWeek;

    public WorkSchedule() {
    }

    public WorkSchedule(Shift shift, DayOfWeek dayOfWeek) {
        this.shift = shift;
        this.dayOfWeek = dayOfWeek;
    }

    public WorkSchedule(Long id, Shift shift, DayOfWeek dayOfWeek) {
        this.id = id;
        this.shift = shift;
        this.dayOfWeek = dayOfWeek;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
